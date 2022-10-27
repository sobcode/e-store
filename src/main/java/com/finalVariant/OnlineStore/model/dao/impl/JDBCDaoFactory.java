package com.finalVariant.OnlineStore.model.dao.impl;

import com.finalVariant.OnlineStore.model.dao.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private final Logger logger = LogManager.getLogger(JDBCDaoFactory.class);
    private DataSource dataSource;
    Connection connection = getConnection();

    @Override
    public CategoryDao createCategoryDao() {
        return new JDBCCategoryDao(connection);
    }

    @Override
    public ColorDao createColorDao() {
        return new JDBCColorDao(connection);
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao(connection);
    }

    @Override
    public ProductDao createProductDao() {
        return new JDBCProductDao(connection);
    }

    @Override
    public SizeDao createSizeDao() {
        return new JDBCSizeDao(connection);
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(connection);
    }

    private Connection getConnection(){
        dataSource = ConnectionPoolHolder.getDataSource(resourcePropertiesName);
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
