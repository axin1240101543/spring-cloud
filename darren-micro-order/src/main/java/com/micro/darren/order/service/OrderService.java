package com.micro.darren.order.service;

import com.micro.darren.order.entity.Order;

public interface OrderService {

    /**
     * 通过订单号获取订单信息
     * @param orderId
     * @return
     */
    Order getOrderById(String orderId);

}
