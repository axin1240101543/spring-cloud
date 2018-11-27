package com.micro.darren.order.service.impl;

import com.micro.darren.order.dao.read.OrderReaderDao;
import com.micro.darren.order.entity.Order;
import com.micro.darren.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderReaderDao orderReaderDao;

    @Override
    public Order getOrderById(String orderId) {
        return orderReaderDao.selectByPrimaryKey(orderId);
    }
}
