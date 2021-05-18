package com.epam.project.dao;

import com.epam.project.dao.imp.ActivityDaoImp;
import com.epam.project.dao.imp.UserDaoImp;
import com.epam.project.exception.DataBaseConnectionException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {
    private static final Logger logger = Logger.getLogger(DAOFactory.class);
    DBManager dbManager = new DBManager();
    Connection connection;

    public UserDao getUserDao() {
        return new UserDaoImp(connection);
    }

    public ActivityDao getActivityDao() {
        return new ActivityDaoImp(connection);
    }

    public void open() throws DataBaseConnectionException {
        connection = dbManager.getConnection();
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }
}
