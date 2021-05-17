package com.example.EpamFinalProject;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String url = resourceBundle.getString("datasource.url");
        String user = resourceBundle.getString("datasource.username");
        String password = resourceBundle.getString("datasource.password");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            logger.error("Cannot obtain a connection from the pool" + throwables);
        }
        return connection;
    }

    public void commitAndClose(Connection con) {
        if (con != null) {
            try {
                con.commit();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


