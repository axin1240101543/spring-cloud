package com.micro.darren.gateway.webservice.order.impl;

import com.micro.darren.common.entity.JsonResult;
import com.micro.darren.common.utils.XmlUtils;
import com.micro.darren.gateway.feign.OrderFeignClient;
import com.micro.darren.gateway.webservice.order.IOrderService;
import com.thoughtworks.xstream.XStream;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * 请求地址：http://localhost:8080/services/IOrderWsdl?wsdl   <![CDATA[<orderId>TD18112320005</orderId>]]>
 *
 * targetNamespace默认为包名倒序
 */
@WebService(name = "IOrderWsdl", targetNamespace = "http://order.webservice.gateway.darren.micro.com/", endpointInterface = "com.micro.darren.gateway.webservice.order.IOrderService")
@Service
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Override
    public String getOderById(String request) {
        return XmlUtils.getXmlToBean2XStream(orderFeignClient.getOrderById(request), "result", JsonResult.class);
    }

}
