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
import com.epam.project.taghandler.PasswordEncoder;
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

    @Override
    public List<User> findAllConnectingUsersByActivity(Integer activity, Integer limit, Integer offset, String order) throws NoUserException {
        List<User> users;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findAllConnectingUsersByActivity(activity, limit, offset, order);
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
    public List<User> findAllUsersWithLimit(Integer limit, Integer offset, String value) throws NoUserException {
        List<User> users;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findAllUsersWithLimit(limit, offset, value);
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
    public Integer calculateUsersInActivity(String value) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            userDao = daoFactory.getUserDao();
            result = userDao.calculateUsersInActivity(value);
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public Integer calculateAllUsers() throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            userDao = daoFactory.getUserDao();
            result = userDao.calculateAllUsers();
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public boolean addUserToActivity(Activity activity, User user) throws NoUserException {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = validateIfUserThere(activity, user) && userDao.addUserToActivity(activity, user);
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();
        }
        return result;
    }

    @Override
    public boolean deleteUserFromActivity(Integer activityId, Integer userId) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = userDao.deleteUserFromActivity(activityId, userId);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    private boolean validateUserData(User user) {
        return !(user.getName() == null
                || user.getName().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getRole() == null);
    }

    private boolean validateRegex(User user) {

        return (user.getName().matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$") &&
                user.getSurname().matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$") &&
                user.getEmail().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") &&
                user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"));
    }

    private boolean validateRegexWithoutPassword(User user){

        return (user.getName().matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$") &&
                user.getSurname().matches("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$") &&
                user.getEmail().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"));
    }


    @Override
    public boolean addUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            System.out.println(validateRegex(user));
            if (validateUserData(user) && validateRegex(user)) {
                user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
                result = userDao.createUser(user);
                daoFactory.close();
            } else {
                return false;
            }
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
            if (validateUserData(user) && validateRegex(user)) {
                user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
                result = userDao.updateUser(user);
                daoFactory.close();
            } else {
                return false;
            }
        } catch (DataBaseConnectionException e) {
            log.error(e);
            return false;
        }
        return result;
    }

    @Override
    public boolean updateUserWithoutEmail(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            if (validateUserData(user) && validateRegexWithoutPassword(user)) {
                user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
                result = userDao.updateUser(user);
                daoFactory.close();
            } else {
                return false;
            }
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
            result = validateUserData(user) && validateRegex(user) && userDao.deleteUser(user);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    private boolean validateIfUserThere(Activity activity, User user) throws DataNotFoundException {
        int i = userDao.checkIfUserAlreadyInThisActivity(String.valueOf(activity.getId()), String.valueOf(user.getId()));
        return !(activity.getCreatedByUserID() == user.getId()) && i == 0 && !user.getRole().equals(Role.ADMIN);
    }


}
