package com.micro.darren.gateway.zuulfilter;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.micro.darren.common.constants.Context;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Component
public class DistributedPreRateLimitZuulFilter extends ZuulFilter {

    private Map<String, RateLimiter> map = Maps.newConcurrentMap();

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 返回值需要大于5（PreDecorationFilter的filterOrder返回值）
     * 否则RequestContext.getCurrentContext()里无法拿到serviceId等数据。
     * @return
     */
    @Override
    public int filterOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletResponse httpServletResponse = requestContext.getResponse();
            String key = null;
            String serviceId = (String)requestContext.get("serviceId");//得到darren-micro-order或者darren-micro-user
            if(null != serviceId){
                //serviceId格式的路由，进入RibbonRoutingFilter
                key = serviceId;
                map.putIfAbsent(key, RateLimiter.create(1000.0));
            }else {
                //URL格式的路由进入SimpleHostRoutingFilter
                URL url = requestContext.getRouteHost();
                if (null != url){
                    key = url.toString();
                    map.putIfAbsent(key, RateLimiter.create(2000.0));
                }
            }
            RateLimiter rateLimiter = map.get(key);
            if (!rateLimiter.tryAcquire()){
                HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
                httpServletResponse.setContentType(MediaType.TEXT_PLAIN_VALUE);
                httpServletResponse.setStatus(httpStatus.value());
                httpServletResponse.getWriter().append(httpStatus.getReasonPhrase());
                requestContext.setSendZuulResponse(false);
                throw new ZuulException(
                        httpStatus.getReasonPhrase(),
                        httpStatus.value(),
                        httpStatus.getReasonPhrase());
            }
        }catch (IOException e){
            ReflectionUtils.rethrowRuntimeException(e);
        }catch (Exception e){
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
