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
    /**
     * find all users
     *
     * @return list of users
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findAllUsers() throws NoUserException;

    /**
     * find  all users by role
     *
     * @param role user role
     * @return finds users by role
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findUsersByRole(Role role) throws NoUserException;

    /**
     * find user by id
     *
     * @param id user id
     * @return user form db
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserById(Integer id) throws NoUserException;

    User findUser(String login, String password) throws NoUserException, WrongPasswordExeption;

    /**
     * find user by login
     *
     * @param login user login
     * @return user by login
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserByLogin(String login) throws NoUserException;
    /**
     * find all users  who connected to activity (participants)
     * @param activityId Activity id to find users from
     * @param limit      first row number
     * @param offset     offset
     * @param order      order
     * @return find user who connected to activity (participants)
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findAllConnectingUsersByActivity(Integer activityId, Integer limit, Integer offset, String order) throws DataNotFoundException, NoUserException;

    /**
     * find all users
     * @param limit  first row number
     * @param offset offset
     * @param value param to insert in db
     * @return list of users
     * @throws DataNotFoundException  if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findAllUsersWithLimit(Integer limit, Integer offset, String value) throws NoUserException;
    /**
     *calculate total user numbers in activity
     * @param  value Activity id
     * @return count of users in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateUsersInActivity(String value) throws DataBaseConnectionException;
    /**
     * @return count of users in DB
     * @throws DataNotFoundException  if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateAllUsers() throws DataBaseConnectionException;

    /**
     * add activities to user
     * @param user User to which activity is added
     * @return true if user was added, false if not
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    boolean addUserToActivity(Activity activity, User user) throws NoUserException;
    /**
     * delete user from activity
     * @param activityId Activity id
     * @param userId User id
     * @return true if operation success and false if fails
     */
    boolean deleteUserFromActivity(Integer activityId, Integer userId);
    /**
     * add user to db
     * @param user User to add to db
     * @return true if operation success and false if fails
     */
    boolean addUser(User user);
    /**
     * update user to db
     * @param user User to update
     * @return true if operation success and false if fails
     */
    boolean updateUser(User user);
    /**
     * update user to db without validation
     * @param user User to update
     * @return true if operation success and false if fails
     */
    boolean updateUserWithoutEmail(User user);
    /**
     * delete user from db
     * @param user User to delete from db
     * @return true if operation success and false if fails
     */
    boolean deleteUser(User user);


}
