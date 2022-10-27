package com.finalVariant.OnlineStore.model.dao.mapper;

import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements Mapper<Order>{
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException, FieldNotPresent {
        return Order.createOrder(rs.getLong("ID"),
                userDao.findById(rs.getLong("user_ID")).orElseThrow(FieldNotPresent::new),
                productDao.findById(rs.getLong("product_ID")).orElseThrow(FieldNotPresent::new),
                rs.getInt("quantity"),
                OrderStatus.valueOf(rs.getString("status")));
    }
}
