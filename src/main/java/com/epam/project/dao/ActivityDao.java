package com.epam.project.dao;

import com.epam.project.entity.Status;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;

import java.util.List;
import java.util.Set;

public interface ActivityDao {

    //find from

    /**
     * find all Activities
     * @return list of Activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findAllActivity() throws DataNotFoundException;

    /**
     * find Activity by id
     * @param id Activity id
     * @return Activity by id
     * @throws DataNotFoundException  if connection is down, broken or unable to retrieve information for certain reasons
     */
    Activity findActivityById(Integer id) throws DataNotFoundException;

    /**
     * find all activities by typeOfActivity
     * @param typeOfActivity Activity typeOfActivity
     * @return list of Activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws DataNotFoundException;

    List<Activity> findAllActivityByTypeOfActivityAndStatusOrderWithoutLimit(String typeOfActivity,String order) throws DataNotFoundException;

    List<Activity> findActivitiesWhereCreatedIdWithoutLimits (Integer id, String order) throws DataNotFoundException;

    List<Activity> findAllActivityByStatsOrderWithoutLimit(String status, String order) throws DataNotFoundException;
    /**
     * find all activities by createdByUserId
     * @param created_id Activity createdByUserId
     * @return list of Activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws DataNotFoundException;


    //find with limit

    /**
     * find first five activities by User id
     * @param id User id
     * @return list of Activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findFirstFiveActivitiesByUserId(Integer id) throws DataNotFoundException;

    /**
     * find all Activities with order
     * @param value value for param
     * @param limit  first row number
     * @param offset offset
     * @param order      order
     * @return list of activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset, String order) throws DataNotFoundException;

    /**
     * find all Activities(created and participant )
     * @param limit  first row number
     * @param offset offset
     * @param order  order
     * @param userId User id
     * @param typeOfActivity Activity typeOfActivity
     * @return list of activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String userId, String typeOfActivity, String order) throws DataNotFoundException;

    /**
     * find all Activities by status name
     * @param limit  first row number
     * @param offset offset
     * @param order  order
     * @param value insert value in db
     * @return list of activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset, String order) throws DataNotFoundException;

    /**
     * find all Activities by typeOfActivity and status "ACCEPT"
     * @param limit  first row number
     * @param offset offset
     * @param orderParam  order
     * @param value insert value in db
     * @return list of activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findActivitiesByTypeOfActivityAndStatusAccept(String value, Integer limit, Integer offset, String orderParam) throws DataNotFoundException;

    /**
     * find all Activities where this user (participant)
     * @param limit  first row number
     * @param offset offset
     * @param status status
     * @param order  order
     * @param userId User id
     * @return list of activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findAllConnectingActivityByUserIdAndStatus(Integer userId, String status, Integer limit, Integer offset, String order) throws DataNotFoundException;

     /**
     * find all Activities where this user (participant)
     * @param limit  first row number
     * @param offset offset
     * @param status status
     * @param order  order
     * @param userId User id
     * @param typeActivity  Activity typeOfActivity
     * @return list of activity
     *  @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findAllConnectingActivityByUserIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity, Integer limit, Integer offset, String order) throws DataNotFoundException;

    /**
     * find all activities by User
     * @param id User id
     * @param order order
     * @param limit limit
     * @param offset offset
     * @param status Activity status
     * @return list of activity
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Activity> findAllActivitiesByCreatedId(Integer id, String order, Integer limit, Integer offset,String status) throws DataNotFoundException;


    /**
     * count all User activities (created and participant)
     * @param id User id
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    //calculate
    Integer calculateActivityNumberWithCreatedByIdCondition(Integer id) throws DataNotFoundException;

    /**
     * count all User activities
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateActivityNumber() throws DataNotFoundException;

    /**
     * count all User activities
     * @param value Activity statusName
     * @return  count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateActivityNumberByStatusName(String value) throws DataNotFoundException;

    /**
     * count all User activities
     * @param userId User id
     * @param typeOfActivity Activity typeOfActivity
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateActivityByCreatedAndTypeActivityCondition(Integer userId, String typeOfActivity) throws DataNotFoundException;

    /**
     *  count all User activities
     * @param typeOfActivity TypeOfActivity
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */

    Integer calculateActivityByTypeOfActivityAndStatusAccepted(String typeOfActivity) throws DataNotFoundException;

    /**
     *  count all User activities
     *  count how many Users in activities
     * @param activityId Activity id
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateNumberOfUsersInActivity(String activityId) throws DataNotFoundException;

    /**
     *  count all users in activity
     * @param userId User id
     * @param status Activity status
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateConnectingActivityByUserIdAndStatus(Integer userId, String status) throws DataNotFoundException;

    /**
     * count all users in activity
     * @param userId User id
     * @param status Activity status
     * @param typeActivity Activity typeOfActivity
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateConnectingActivityByUsersIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity) throws DataNotFoundException;

    /**
     *  count all User activities
     * @param userId User id
     * @param status Activity status
     * @return count of activities in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateActivityByCreatedId(Integer userId,String status) throws DataNotFoundException;
    //crud

    /**
     * add Users to activity
     * @param activity Activity
     * @return set of numbers users
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Set<Integer> addUsersToActivities(Activity activity) throws DataNotFoundException;

    /**
     * add user to db
     * @param activity Actvity to add to db
     * @return true if operation success and false if fails
     */
    boolean createActivity(Activity activity);
    /**
     * update user to db
     * @param activity Activity to update
     * @return true if operation success and false if fails
     */
    boolean updateActivity(Activity activity);
    /**
     * delete user from db
     * @param activity Activity to delete from db
     * @return true if operation success and false if fails
     */
    boolean deleteActivity(Activity activity);

}
