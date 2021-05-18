package com.epam.project.dao;

import com.epam.project.exception.DataNotFoundException;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public abstract class GenericAbstractDao<T> {
    private static final Logger log = Logger.getLogger(GenericAbstractDao.class);
    private Mapper<T, PreparedStatement> mapperToDB;
    private Mapper<ResultSet, T> mapperFromDB;

    protected GenericAbstractDao() {

    }

    protected void setMapperToDB(Mapper<T, PreparedStatement> mapperToDB) {
        this.mapperToDB = mapperToDB;
    }

    protected void setMapperFromDB(Mapper<ResultSet, T> mapperFromDB) {
        this.mapperFromDB = mapperFromDB;
    }

    protected List<T> findAll(Connection connection, Class<T> t, String SQL_getAll) throws DataNotFoundException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_getAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataNotFoundException();
        }
        return items;
    }

    protected List<T> findAllFromTo(Connection connection, Class<T> t, Integer first, Integer offset, String SQL_getAll_base)
            throws DataNotFoundException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_getAll_base + " limit " + first + ", " + offset + ";");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataNotFoundException();
        }
        return items;
    }

    protected <V> T findBy(Connection connection, Class<T> t, String SQL_selectByParameter, V value)
            throws DataNotFoundException {
        T item = getItemInstance(t);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, item);
            else
                throw new DataNotFoundException();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataNotFoundException();
        }
        return item;
    }

    protected <V> List<T> findAsListBy(Connection connection, Class<T> t, String SQL_selectByParameter, V value)
            throws DataNotFoundException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataNotFoundException();
        }
        return items;
    }

    protected boolean addToDB(Connection connection, T item, String SQL_addNew) {
        boolean result;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_addNew);
            mapperToDB.map(item, preparedStatement);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            log.error(sqle);
            return false;
        }
        return result;
    }

    protected <V> boolean updateInDB(Connection connection, T item, String SQL_update, Integer paramNum, V value) {
        boolean result;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_update);
            mapperToDB.map(item, preparedStatement);
            addParameterToPreparedStatement(preparedStatement, paramNum, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            log.error(sqle);
            return false;
        }
        return result;
    }

    protected <V> boolean deleteFromDB(Connection connection, String SQL_delete, V value) {
        boolean result;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_delete);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            log.error(sqle);
            return false;
        }
        return result;
    }

    private T getItemInstance(Class<T> t) {
        T item = null;
        try {
            item = t.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ie) {
            log.error(ie);
        }
        return item;
    }

    private <V> void addParameterToPreparedStatement(PreparedStatement preparedStatement, Integer paramNum, V value)
            throws SQLException {
        if (value instanceof String)
            preparedStatement.setString(paramNum, (String) value);
        if (value instanceof Integer)
            preparedStatement.setInt(paramNum, (Integer) value);
        if (value instanceof Timestamp)
            preparedStatement.setTimestamp(paramNum, (Timestamp) value);
    }


}
