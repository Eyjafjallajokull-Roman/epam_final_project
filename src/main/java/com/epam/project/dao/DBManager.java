package com.epam.project.dao;

import com.epam.project.exception.DataBaseConnectionException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class);
    private static BasicDataSource basicDataSource;
    private Connection connection;

    public DBManager() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String url = resourceBundle.getString("datasource.url");
        String user = resourceBundle.getString("datasource.username");
        String password = resourceBundle.getString("datasource.password");
        int minIdle = Integer.parseInt(resourceBundle.getString("datasource.minIdle"));
        int maxIdle = Integer.parseInt(resourceBundle.getString("datasource.maxIdle"));
        int maxActive = Integer.parseInt(resourceBundle.getString("datasource.maxActive"));
        int maxOpenPStatements = Integer.parseInt(resourceBundle.getString("datasource.maxOpenPreparedStatements"));
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setMinIdle(minIdle);
        basicDataSource.setMaxIdle(maxIdle);
        basicDataSource.setMaxTotal(maxActive);
        basicDataSource.setMaxOpenPreparedStatements(maxOpenPStatements);
    }


    public Connection getConnection() throws DataBaseConnectionException {
        Connection connection = null;
        try {
            connection = basicDataSource.getConnection();
        } catch (SQLException throwables) {
            logger.error("Cannot obtain a connection from the pool" + throwables);
            throw new DataBaseConnectionException();
        }
        return connection;
    }



}


