package com.joseph.supplier.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setBatchId(rs.getString("batchId"));
        order.setStyle(rs.getString("style"));
        order.setOrderDate(rs.getString("orderDate"));
        order.setFinishCount(rs.getInt("finishCount"));
        order.setSize(rs.getString("size"));
        order.setDeliveryDate(rs.getString("deliveryDate"));
        order.setOutCount(rs.getInt("outCount"));
        order.setColor(rs.getString("color"));
        order.setPrice(rs.getString("price"));
        order.setBrand(rs.getString("brand"));
        return order;
    }
}