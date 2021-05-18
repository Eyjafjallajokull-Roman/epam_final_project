package com.epam.project.service.imp;

import com.epam.project.constants.Role;
import com.epam.project.dao.DAOFactory;
import com.epam.project.dao.UserDao;
import com.epam.project.entity.User;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.UserService;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import org.apache.log4j.Logger;

import java.util.List;

public class UserServiceImp implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImp.class);
    private static final DAOFactory daoFactory = new DAOFactory();
    private static UserDao userDao;


    @Override
    public List<User> findAllUsers() throws NoUserException {
        List<User> users;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findAllUsers();
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(Role role) throws NoUserException {
        List<User> users;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findUsersByRole(role);
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();
        }
        return users;
    }

    @Override
    public User findUser(String login, String password) throws NoUserException {
        User user = new User();
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            user = userDao.findUserByLogin(login);
            daoFactory.close();
            //toDo validation
            return user;
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();

        }
    }

    @Override
    public boolean addUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            //todo validate user
            result = userDao.createUser(user);
            daoFactory.close();
        } catch (DataBaseConnectionException e) {
            log.error(e);
            return false;
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            //todo validate user
            result = userDao.updateUser(user);
        } catch (DataBaseConnectionException e) {
            log.error(e);
            return false;
        }
        return result;
    }

    @Override
    public boolean deleteUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            //todo validate user
            result = userDao.deleteUser(user);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }
}
