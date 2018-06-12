package com.joseph.supplier.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderCountRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order record = new Order();
        record.setOrderDate(rs.getString("order_date"));
        record.setStyle(rs.getString("style"));
        record.setColor(rs.getString("color"));
        record.setOutCount(rs.getInt("out_count"));
        record.setFinishCount(rs.getInt("finish_count"));
        return record;
    }
}