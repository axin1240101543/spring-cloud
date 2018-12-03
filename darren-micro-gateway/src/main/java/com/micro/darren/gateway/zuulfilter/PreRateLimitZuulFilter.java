package com.micro.darren.gateway.zuulfilter;

import com.google.common.util.concurrent.RateLimiter;
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

/**
 * 限流过滤器
 * 基于Google Guava
 * 参考文档：
 * http://www.itmuch.com/spring-cloud/zuul/spring-cloud-zuul-filter/         Spring Cloud限流详解
 * https://www.jianshu.com/p/8f548e469bbe
 * https://www.cnblogs.com/LBSer/p/4083131.html                              接口限流实践
 * http://www.itmuch.com/spring-cloud-sum/spring-cloud-ratelimit/            Spring Cloud Zuul过滤器详解
 * http://www.itmuch.com/spring-cloud/zuul/zuul-filter-in-spring-cloud/      Spring Cloud内置的Zuul过滤器详解
 */
@Component
public class PreRateLimitZuulFilter extends ZuulFilter{

    private final RateLimiter rateLimiter = RateLimiter.create(1000.0);

    /**
     * 返回过滤器的类型。有pre、route、post、error等几种取值，分别对应上文的几种过滤器。
     * 详细可以参考com.netflix.zuul.ZuulFilter.filterType() 中的注释
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 返回一个int值来指定过滤器的执行顺序，不同的过滤器允许返回相同的数字。
     * @return
     */
    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 返回一个boolean值来判断该过滤器是否要执行，true表示执行，false表示不执行。（限流开启开关）
     * 开启：true
     * 关闭：false
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletResponse httpServletResponse = requestContext.getResponse();
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
