package com.micro.darren.order.controller;

import com.micro.darren.common.entity.JsonResult;
import com.micro.darren.common.utils.XmlUtils;
import com.micro.darren.order.entity.Order;
import com.micro.darren.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 通过订单号获取订单信息
     * @param params
     * @return
     */
    @PostMapping("/getOrderById")
    public JsonResult getOrderById(@RequestBody String params){
        log.info("接口名称:{}, 请求参数：{}", "getOrderById", params);
        String orderId = XmlUtils.getStringFirstToXml2Jsoup(params, "orderId");
        Order order = orderService.getOrderById(orderId);
        log.info("接口名称:{}, 响应参数：{}", "getOrderById", order);
        JsonResult result = new JsonResult();
        result.setResultCode(HttpStatus.OK.value());
        result.setResultMessage(HttpStatus.OK.getReasonPhrase());
        result.setData(order);
        return result;
    }

}
