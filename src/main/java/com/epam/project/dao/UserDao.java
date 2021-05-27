package com.epam.project.dao;

import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserDao {
    List<User> findAllUsers() throws DataNotFoundException;

    User findUserById(Integer id) throws DataNotFoundException;

    User findUserByLogin(String login) throws DataNotFoundException;

    List<User> findUsersByRole(Role role) throws DataNotFoundException;

    List<User> findAllConnectingUsersByActivity(Integer activityId, Integer limit, Integer offset) throws DataNotFoundException;

    Set<Integer> addActivitiesToUser(User user) throws DataNotFoundException;

    List<User> findAllUsersWithLimit(Integer limit, Integer offset, String value) throws DataNotFoundException;


    Integer checkIfUserAlreadyInThisActivity(String activityId, String userId) throws DataNotFoundException;

    Integer calculateUsersInActivity(String value) throws DataNotFoundException;

    Integer calculateAllUsers() throws DataNotFoundException;

    boolean addUserToActivity(Activity activity, User user) throws DataNotFoundException;

    boolean deleteUserFromActivity(Integer activityId, Integer userId);

    boolean createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);


}
