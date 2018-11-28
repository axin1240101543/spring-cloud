package com.micro.darren.gateway.feign.fallbackfactory;

import com.micro.darren.common.entity.JsonResult;
import com.micro.darren.gateway.feign.OrderFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 配置Feign的FallbackFactory
 * FallBackFactory能够捕捉异常信息
 */
@Component
@Slf4j
public class HystrixClientFallbackFactory implements FallbackFactory<OrderFeignClient> {

    @Override
    public OrderFeignClient create(Throwable throwable) {
        return new HystrixClientWithFallBackFactory() {
            @Override
            public JsonResult getOrderById(String params) {
                log.error("进入Feign断路器，异常原因:", throwable);
                JsonResult result = new JsonResult();
                result.setResultCode(HttpStatus.REQUEST_TIMEOUT.value());
                result.setResultMessage(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
                return result;
            }
        };
    }

}
