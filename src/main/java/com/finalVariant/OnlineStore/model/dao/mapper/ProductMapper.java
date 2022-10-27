package com.finalVariant.OnlineStore.model.dao.mapper;

import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements Mapper<Product>{
    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException, FieldNotPresent {
        return Product.createProduct(rs.getLong("ID"),
                                     rs.getString("name"),
                                     rs.getInt("price"),
                                     Product.Sex.valueOf(rs.getString("sex")),
                                     rs.getString("image"),
                                     categoryDao.findById(rs.getLong("category_ID")).orElseThrow(FieldNotPresent::new),
                                     colorDao.findById(rs.getLong("color_ID")).orElseThrow(FieldNotPresent::new),
                                     sizeDao.findById(rs.getLong("size_ID")).orElseThrow(FieldNotPresent::new));
    }
}
