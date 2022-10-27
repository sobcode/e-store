package com.finalVariant.OnlineStore.model.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    public static DataSource getDataSource(String dbParamResourceName){
        if(dataSource == null){
            synchronized (ConnectionPoolHolder.class){
                ResourceBundle dbParam = ResourceBundle.getBundle(dbParamResourceName);

                BasicDataSource ds = new BasicDataSource();
                ds.setDriverClassName(dbParam.getString("db.driverClassName"));
                ds.setUrl(dbParam.getString("db.url"));
                ds.setUsername(dbParam.getString("db.username"));
                ds.setPassword(dbParam.getString("db.password"));
                ds.setMinIdle(5);
                ds.setMaxIdle(10);
                ds.setMaxOpenPreparedStatements(100);
                dataSource = ds;
            }
        }
        return dataSource;
    }
}
