package com.micro.darren.gateway.feign;

import com.micro.darren.common.entity.JsonResult;
import com.micro.darren.gateway.feign.fallbackfactory.HystrixClientFallbackFactory;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * name:调用微服务的spring.application.name
 * 配置feign的断路器：Feign的FallBack和FallBackFactory不能同时配置
 */
//@FeignClient(name = "darren-micro-order", fallback = MyFeignForHystrix.class)
@FeignClient(name = "darren-micro-order", fallbackFactory = HystrixClientFallbackFactory.class)
public interface OrderFeignClient {

    /**
     *  通过订单号获取订单信息
     * note：
     * 1、不能使用@GetMapping注解
     * 2、使用@PathVariable注解得设置value
     * @param params
     * @return
     */
    @RequestMapping(value = "/order/getOrderById", method = RequestMethod.POST)
    JsonResult getOrderById(@RequestBody String params);

}
