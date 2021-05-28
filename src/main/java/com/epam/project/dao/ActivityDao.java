package com.epam.project.dao;

import com.epam.project.entity.TypeOfActivity;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;

import java.util.List;
import java.util.Set;

public interface ActivityDao {

    //find from
    List<Activity> findAllActivity() throws DataNotFoundException;

    Activity findActivityById(Integer id) throws DataNotFoundException;

    List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws DataNotFoundException;

    List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws DataNotFoundException;


    //find with limit

    List<Activity> findFirstFiveActivitiesByUserId(Integer id) throws DataNotFoundException;

    List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset,String order) throws DataNotFoundException;

    List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String value1, String value2, String order) throws DataNotFoundException;

    List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset, String order) throws DataNotFoundException;

    List<Activity> findActivitiesByTypeOfActivityAndStatusAccept(String value, Integer limit, Integer offset, String orderParam) throws DataNotFoundException;

    List<Activity> findAllConnectingActivityByUserIdAndStatus(Integer userId, String status, Integer limit, Integer offset, String order) throws DataNotFoundException;

    List<Activity> findAllConnectingActivityByUserIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity, Integer limit, Integer offset, String order) throws DataNotFoundException;


    //calculate
    Integer calculateActivityNumberWithCreatedByIdCondition(String value) throws DataNotFoundException;

    Integer calculateActivityNumber() throws DataNotFoundException;

    Integer calculateActivityNumberByStatusName(String value) throws DataNotFoundException;

    Integer calculateActivityByCreatedAndTypeActivityCondition(String value1, String value2) throws DataNotFoundException;

    Integer calculateActivityByTypeOfActivityAndStatusAccepted(String value) throws DataNotFoundException;

    Integer calculateNumberOfUsersInActivity(String value) throws DataNotFoundException;

    Integer calculateConnectingActivityByUserIdAndStatus(Integer userId, String status) throws DataNotFoundException;

    Integer calculateConnectingActivityByUsersIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity) throws DataNotFoundException;
    //crud

    Set<Integer> addUsersToActivities(Activity activity) throws DataNotFoundException;

    boolean createActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(Activity activity);
}
