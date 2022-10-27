package com.finalVariant.OnlineStore.model.dao.mapper;

import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SizeMapper implements Mapper<Product.Size>{
    @Override
    public Product.Size extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.Size.createSize(rs.getLong("ID"), rs.getString("size"));
    }
}
