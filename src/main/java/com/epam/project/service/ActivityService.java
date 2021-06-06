package com.epam.project.service;

import com.epam.project.entity.Activity;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;

import java.util.List;

public interface ActivityService {
    /**
     * find all Activities
     * @return list of Activity
     * @throws NoSuchActivityException if Activity was not found throw exception
     */
    List<Activity> findAllActivity() throws NoSuchActivityException;

    /**
     * find Activity by id
     * @param id Activity id
     * @return Activity by id
     * @throws NoSuchActivityException if Activity was not found throw exception
     * */

    Activity findActivityById(Integer id) throws NoSuchActivityException;

    /**
     * find all activities by typeOfActivity
     * @param typeOfActivity Activity typeOfActivity
     * @return list of Activity
     * @throws NoSuchActivityException if Activity was not found throw exception     */
    List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws NoSuchActivityException;

    /**
     * find all activities by createdByUserId
     * @param created_id Activity createdByUserId
     * @return list of Activity
     * @throws NoSuchActivityException if Activity was not found throw exception
     */
    List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws NoSuchActivityException;
    /**
     * find first five activities by User id
     * @param id User id
     * @return list of Activity
     * @throws NoUserException if User was not found throw exception
     */
    List<Activity> findFirstFiveActivitiesByUserId(Integer id) throws NoUserException;

    List<Activity> findAllActivityByTypeOfActivityAndStatusOrderWithoutLimit(String typeOfActivity,String order) throws  NoSuchActivityException;

    List<Activity> findAllActivityByStatsOrderWithoutLimit(String status, String order) throws NoSuchActivityException;

    /**
     * count all User activities
     * @return count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */

    Integer calculateActivityNumber() throws DataBaseConnectionException;
    /**
     * count all User activities
     * @param value Activity statusName
     * @return  count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */
    Integer calculateNumberOfUsersInActivity(String value) throws DataBaseConnectionException;

    /**
     * count all User activities
     * @param userId User id
     * @return count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */
    Integer calculateActivityNumberByStatusName(String userId) throws DataBaseConnectionException;

    /**
     *  count all User activities
     * @param userId User id
     * @param status Activity status
     * @return count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */

    Integer calculateActivityByCreatedId(Integer userId,String status) throws DataBaseConnectionException;

    /**
     * find all Activities with order
     * @param id User id
     * @param limit  first row number
     * @param offset offset
     * @param order      order
     * @return list of activity
     * @throws NoSuchActivityException if Activity was not found throw exception
     */

    List<Activity> findAllActivitiesByCreatedId(Integer id, String order, Integer limit, Integer offset,String status) throws NoSuchActivityException;

    /**
     * find all Activities by typeOfActivity and status "ACCEPT"
     * @param limit  first row number
     * @param offset offset
     * @param order  order
     * @param value insert value in db
     * @return list of activity
     * @throws NoSuchActivityException if Activity was not found throw exception
     */

    List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset, String order) throws NoSuchActivityException;


    /**
     * find all Activities with order
     * @param value value for param
     * @param limit  first row number
     * @param offset offset
     * @param order      order
     * @return list of activity
     * @throws NoSuchActivityException if Activity was not found throw exception
     */
    List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset, String order) throws NoSuchActivityException;
    /**
     * find all Activities(created and participant )
     * @param limit  first row number
     * @param offset offset
     * @param order  order
     * @param userId User id
     * @param typeOfActivity Activity typeOfActivity
     * @return list of activity
     * @throws NoSuchActivityException if Activity was not found throw exception
     */



    List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String userId, String typeOfActivity,String order) throws NoSuchActivityException;

    List<Activity> findActivitiesWhereCreatedIdWithoutLimits (Integer id, String order) throws NoSuchActivityException;
    /**
     * find all Activities by typeOfActivity and status "ACCEPT"
     * @param limit  first row number
     * @param offset offset
     * @param orderParam  order
     * @param value insert value in db
     * @return list of activity
     * @throws NoSuchActivityException if Activity was not found throw exception
     */

    List<Activity> findActivitiesByTypeOfActivityAndStatusAccept(String value, Integer limit, Integer offset, String orderParam) throws NoSuchActivityException;
    /**
     * find all Activities where this user (participant)
     * @param limit  first row number
     * @param offset offset
     * @param status status
     * @param order  order
     * @param userId User id
     * @return list of activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     * @throws NoSuchActivityException if Activity was not found throw exception
     */

    List<Activity> findAllConnectingActivityByUserIdAndStatus(Integer userId, String status, Integer limit, Integer offset, String order) throws DataNotFoundException, NoSuchActivityException;
    /**
     * find all Activities where this user (participant)
     * @param limit  first row number
     * @param offset offset
     * @param status status
     * @param order  order
     * @param userId User id
     * @param typeActivity  Activity typeOfActivity
     * @return list of activity
     *  @throws NoSuchActivityException if Activity was not found throw exception
     */
    List<Activity> findAllConnectingActivityByUserIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity, Integer limit, Integer offset, String order) throws NoSuchActivityException;

    /**
     * count all User activities
     * @param userId User id
     * @param typeOfActivity Activity typeOfActivity
     * @return count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */

    Integer calculateActivityNumberWithCreatedByIdConditionAndTypeActivity(Integer userId, String typeOfActivity) throws DataBaseConnectionException;
    /**
     * count all User activities (created and participant)
     * @param id User id
     * @return count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */
    Integer calculateActivityNumberWithCreatedByIdCondition(Integer id) throws DataBaseConnectionException;
    /**
     *  count all User activities
     * @param typeOfActivity TypeOfActivity
     * @return count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */
    Integer calculateActivityByTypeOfActivityAndStatusAccepted(String typeOfActivity) throws DataBaseConnectionException;
    /**
     *  count all users in activity
     * @param userId User id
     * @param status Activity status
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     * @throws DataBaseConnectionException if unable to close connection
     */
    Integer calculateConnectingActivityByUserIdAndStatus(Integer userId, String status) throws DataNotFoundException, DataBaseConnectionException;
    /**
     * count all users in activity
     * @param userId User id
     * @param status Activity status
     * @param typeActivity Activity typeOfActivity
     * @return count of activities in DB
     * @throws DataBaseConnectionException if unable to close connection
     */
    Integer calculateConnectingActivityByUsersBIdAndStatusAndType(Integer userId, String status, String typeActivity) throws DataBaseConnectionException;

    /**
     * update user to db
     * @param activity Activity to update
     * @return true if operation success and false if fails
     */

    boolean addActivity(Activity activity);
    /**
     * update user to db
     * @param activity Activity to update
     * @return true if operation success and false if fails
     */
    boolean updateActivity(Activity activity);
    /**
     * update user to db without validation
     * @param activity Activity to update
     * @return true if operation success and false if fails
     */
    boolean updateActivityWithoutValidation(Activity activity);
    /**
     * delete user from db
     * @param activity Activity to delete from db
     * @return true if operation success and false if fails
     */
    boolean deleteActivity(Activity activity);
}
