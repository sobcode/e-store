package com.finalVariant.OnlineStore.model.service;

import com.finalVariant.OnlineStore.model.dao.*;
import com.finalVariant.OnlineStore.model.dao.impl.JDBCDaoFactory;

public interface Service {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "db");

    CategoryDao categoryDao = daoFactory.createCategoryDao();
    UserDao userDao = daoFactory.createUserDao();
    OrderDao orderDao = daoFactory.createOrderDao();
    SizeDao sizeDao = daoFactory.createSizeDao();
    ColorDao colorDao = daoFactory.createColorDao();
    ProductDao productDao = daoFactory.createProductDao();
}
