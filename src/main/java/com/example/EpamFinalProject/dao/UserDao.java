package com.example.EpamFinalProject.dao;

import com.example.EpamFinalProject.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAllUsers();

    void createUser(User user);

    boolean updateUser(int toModifyUser, User current);

    boolean deleteUser(User user);
}
