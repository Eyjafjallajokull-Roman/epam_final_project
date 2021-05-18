package com.epam.project.dao;

import com.epam.project.constants.Role;
import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;

import java.util.List;

public interface UserDao {
    List<User> findAllUsers() throws DataNotFoundException;

    User findUserById(Integer id) throws DataNotFoundException;

    User findUserByLogin(String login) throws DataNotFoundException;

    List<User> findUsersByRole(Role role) throws DataNotFoundException;

    boolean createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
