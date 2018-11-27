package com.micro.darren.order.dao.write;

import com.micro.darren.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderWriterDao {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}