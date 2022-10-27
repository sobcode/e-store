package com.finalVariant.OnlineStore.model.dao.mapper;

import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorMapper implements Mapper<Product.Color>{
    @Override
    public Product.Color extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.Color.createColor(rs.getLong("ID"), rs.getString("color"));
    }
}
