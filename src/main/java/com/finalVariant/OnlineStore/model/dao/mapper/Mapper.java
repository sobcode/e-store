package com.finalVariant.OnlineStore.model.dao.mapper;

import com.finalVariant.OnlineStore.model.dao.*;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.dao.impl.JDBCDaoFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "db");

    CategoryDao categoryDao = daoFactory.createCategoryDao();
    ColorDao colorDao = daoFactory.createColorDao();
    SizeDao sizeDao = daoFactory.createSizeDao();
    UserDao userDao = daoFactory.createUserDao();
    ProductDao productDao = daoFactory.createProductDao();

    T extractFromResultSet(ResultSet rs) throws SQLException, FieldNotPresent;
}
