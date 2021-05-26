package com.epam.project.service.imp;

import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.dao.DAOFactory;
import com.epam.project.dao.UserDao;
import com.epam.project.entity.User;
import com.epam.project.exception.NoUserException;
import com.epam.project.exception.WrongPasswordExeption;
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
            for (User user : users) {
                userDao.addActivitiesToUser(user);
            }
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
            for (User user : users) {
                userDao.addActivitiesToUser(user);
            }
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();
        }
        return users;
    }

    @Override
    public User findUserById(Integer id) throws NoUserException {
        User user = new User();
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            user = userDao.findUserById(id);
            userDao.addActivitiesToUser(user);
            daoFactory.close();
            return user;
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();

        }
    }

    @Override
    public User findUser(String login, String password) throws NoUserException, WrongPasswordExeption {
        User user = new User();
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            user = userDao.findUserByLogin(login);
            userDao.addActivitiesToUser(user);
            daoFactory.close();
            if (!user.getPassword().equals(password))
                throw new WrongPasswordExeption();
            return user;
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();

        }
    }

    @Override
    public User findUserByLogin(String login) throws NoUserException {
        User user;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            user = userDao.findUserByLogin(login);
            userDao.addActivitiesToUser(user);
            daoFactory.close();
            return user;
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();
        }

    }

    private boolean validateUserData(User user) {
        return !(user.getName() == null
                || user.getName().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getRole() == null);
    }

    private boolean validateRegex(User user){

        return (user.getName().matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$") &&
                user.getSurname().matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$")&&
                user.getEmail().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")&&
                user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"));
    }


    @Override
    public boolean addUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            System.out.println(validateRegex(user));
            result = validateUserData(user) && userDao.createUser(user)  && validateRegex(user);
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
            result = validateUserData(user) && userDao.updateUser(user) && validateRegex(user);
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
            result = validateUserData(user) && userDao.deleteUser(user) && validateRegex(user);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }




}
