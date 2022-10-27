package com.finalVariant.OnlineStore.model.dao;

import com.finalVariant.OnlineStore.model.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends GenericDao<Product>{
    List<Product> findProductWithSortLimitAndFilter(String sortBy, String order, String[] filterParams, int from, int to);

    int findAmountOfProductsWithFilter(String[] filterParams);

    boolean updateAllProducts(Product... products) throws SQLException;
}
