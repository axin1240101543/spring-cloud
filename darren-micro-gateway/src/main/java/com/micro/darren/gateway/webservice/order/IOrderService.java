package com.micro.darren.gateway.webservice.order;

import com.alibaba.fastjson.JSONObject;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://order.webservice.gateway.darren.micro.com/")
public interface IOrderService {

    /**
     * 通过订单号获取订单信息
     * @param request
     * @return
     */
    @WebMethod(action = "getOderById")
    String getOderById(@WebParam(name = "request")String request);

}
