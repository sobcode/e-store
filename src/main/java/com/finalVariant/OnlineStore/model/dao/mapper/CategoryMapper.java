package com.finalVariant.OnlineStore.model.dao.mapper;

import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements Mapper<Product.Category>{
    @Override
    public Product.Category extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.Category.createCategory(rs.getLong("ID"), rs.getString("category"));
    }
}
