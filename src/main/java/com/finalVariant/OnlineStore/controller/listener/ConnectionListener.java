package com.finalVariant.OnlineStore.controller.listener;

import com.finalVariant.OnlineStore.model.dao.impl.ConnectionPoolHolder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This listener checks accessibility to the database.
 *
 * @author Artem Sobko
 * @version 1.0
 * @since 07.12.2022
 */
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
