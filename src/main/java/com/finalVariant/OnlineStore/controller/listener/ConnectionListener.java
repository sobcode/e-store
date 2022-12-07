package com.finalVariant.OnlineStore.controller.listener;

import com.finalVariant.OnlineStore.model.dao.DaoFactory;
import com.finalVariant.OnlineStore.model.dao.impl.ConnectionPoolHolder;
import com.finalVariant.OnlineStore.model.dao.impl.JDBCDaoFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ConnectionListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource dataSource = ConnectionPoolHolder.getDataSource("db");
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
