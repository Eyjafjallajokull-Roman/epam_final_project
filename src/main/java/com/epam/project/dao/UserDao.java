package com.epam.project.dao;

import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserDao {


    /**
     * find all users
     *
     * @return list of users
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findAllUsers() throws DataNotFoundException;


    /**
     * find user by id
     *
     * @param id user id
     * @return user form db
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserById(Integer id) throws DataNotFoundException;

    /**
     * find user by login
     *
     * @param login user login
     * @return user by login
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserByLogin(String login) throws DataNotFoundException;

    /**
     * find  all users by role
     *
     * @param role user role
     * @return finds users by role
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findUsersByRole(Role role) throws DataNotFoundException;


    /**
     * find all users  who connected to activity (participants)
     *
     * @param activityId Activity id to find users from
     * @param limit      first row number
     * @param offset     offset
     * @param order      order
     * @return find user who connected to activity (participants)
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findAllConnectingUsersByActivity(Integer activityId, Integer limit, Integer offset, String order) throws DataNotFoundException;

    /**
     * add activities to user
     *
     * @param user User to which activity is added
     * @return set of users
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Set<Integer> addActivitiesToUser(User user) throws DataNotFoundException;


    /**
     * find all users
     *
     * @param limit  first row number
     * @param offset offset
     * @param param  param to insert in db
     * @return list of users
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findAllUsersWithLimit(Integer limit, Integer offset, String param) throws DataNotFoundException;


    /**
     * check if user in activity
     *
     * @param activityId Activity id
     * @param userId     User id
     * @return int if user is there
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer checkIfUserAlreadyInThisActivity(String activityId, String userId) throws DataNotFoundException;

    /**
     * calculate total user numbers in activity
     *
     * @param value Activity id
     * @return count of users in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateUsersInActivity(String value) throws DataNotFoundException;


    /**
     * @return count of users in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateAllUsers() throws DataNotFoundException;

    /**
     * add user to activity
     *
     * @param activity Activity to add User
     * @param user     User to add to Activity
     * @return true if operation success and false if fails
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    boolean addUserToActivity(Activity activity, User user) throws DataNotFoundException;

    /**
     * delete user from activity
     *
     * @param activityId Activity id
     * @param userId     User id
     * @return true if operation success and false if fails
     */
    boolean deleteUserFromActivity(Integer activityId, Integer userId);

    /**
     * add user to db
     *
     * @param user User to add to db
     * @return true if operation success and false if fails
     */
    boolean createUser(User user);

    /**
     * update user to db
     *
     * @param user User to update
     * @return true if operation success and false if fails
     */
    boolean updateUser(User user);

    /**
     * delete user from db
     *
     * @param user User to delete from db
     * @return true if operation success and false if fails
     */
    boolean deleteUser(User user);


}
