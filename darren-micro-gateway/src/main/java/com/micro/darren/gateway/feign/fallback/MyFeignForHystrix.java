package com.micro.darren.gateway.feign.fallback;

import com.micro.darren.common.entity.JsonResult;
import com.micro.darren.gateway.feign.OrderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 配置Feign的Fallback
 */
@Component
@Slf4j
public class MyFeignForHystrix implements OrderFeignClient{

    @Override
    public JsonResult getOrderById(String params) {
        log.error("进入Feign断路器");
        JsonResult result = new JsonResult();
        result.setResultCode(HttpStatus.REQUEST_TIMEOUT.value());
        result.setResultMessage(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
        return result;
    }
}
