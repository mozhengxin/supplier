package com.joseph.supplier.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordRowMapper implements RowMapper<Record> {

    @Override
    public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
        Record record = new Record();
        record.setSupplierName(rs.getString("supplier_name"));
        record.setContent(rs.getString("content"));
        record.setStyle(rs.getString("style"));
        record.setCtime(rs.getDate("ctime"));
        return record;
    }
}