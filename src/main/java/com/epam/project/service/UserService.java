package com.epam.project.service;

import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoUserException;
import com.epam.project.entity.Role;
import com.epam.project.exception.WrongPasswordExeption;

import java.util.List;

public interface UserService {
    List<User> findAllUsers() throws NoUserException;

    List<User> findUsersByRole(Role role) throws NoUserException;

    User findUserById(Integer id) throws NoUserException;

    User findUser(String login, String password) throws NoUserException, WrongPasswordExeption;

    User findUserByLogin(String login) throws NoUserException;

    List<User> findAllConnectingUsersByActivity(Integer activity, Integer limit, Integer offset) throws DataNotFoundException, NoUserException;

    List<User> findAllUsersWithLimit(Integer limit, Integer offset, String value) throws NoUserException;

    Integer calculateUsersInActivity(String value) throws DataBaseConnectionException;

    Integer calculateAllUsers() throws DataBaseConnectionException;

    boolean addUserToActivity(Activity activity, User user) throws NoUserException;

    boolean deleteUserFromActivity(Integer activityId, Integer userId);

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);


}
