package com.micro.darren.order.dao.read;

import com.micro.darren.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderReaderDao {

    /**
     * 通过订单号获取订单信息
     * @param orderId
     * @return
     */
    Order selectByPrimaryKey(String orderId);

}