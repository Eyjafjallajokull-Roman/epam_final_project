package com.epam.project.service;

import com.epam.project.entity.User;
import com.epam.project.exception.NoUserException;
import com.epam.project.constants.Role;
import com.epam.project.exception.WrongPasswordExeption;

import java.util.List;

public interface UserService {
    List<User> findAllUsers() throws NoUserException;

    List<User> findUsersByRole(Role role) throws NoUserException;

    User findUser(String login, String password) throws NoUserException, WrongPasswordExeption;

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
