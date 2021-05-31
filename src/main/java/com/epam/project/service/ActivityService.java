package com.epam.project.service;

import com.epam.project.entity.Activity;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;

import java.util.List;

public interface ActivityService {
    List<Activity> findAllActivity() throws NoSuchActivityException;

    Activity findActivityById(Integer id) throws NoSuchActivityException;

    List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws NoSuchActivityException;

    List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws NoSuchActivityException;

    List<Activity> findFirstFiveActivitiesByUserId(Integer id) throws NoUserException;

    Integer calculateActivityNumber() throws DataBaseConnectionException;

    Integer calculateNumberOfUsersInActivity(String value) throws DataBaseConnectionException;

    Integer calculateActivityNumberByStatusName(String value) throws DataBaseConnectionException;

    Integer calculateActivityByCreatedId(Integer userId) throws DataBaseConnectionException;

    List<Activity> findAllActivitiesByCreatedId(Integer id, String order, Integer limit, Integer offset) throws NoSuchActivityException;

    List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset, String order) throws NoSuchActivityException;

    List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset, String order) throws NoSuchActivityException;

    List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String value1, String value2,String order) throws NoSuchActivityException;

    List<Activity> findActivitiesByTypeOfActivityAndStatusAccept(String value, Integer limit, Integer offset, String orderParam) throws NoSuchActivityException;

    List<Activity> findAllConnectingActivityByUserIdAndStatus(Integer userId, String status, Integer limit, Integer offset, String order) throws DataNotFoundException, NoSuchActivityException;

    List<Activity> findAllConnectingActivityByUserIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity, Integer limit, Integer offset, String order) throws NoSuchActivityException;

    Integer calculateActivityNumberWithCreatedByIdConditionAndTypeActivity(Integer value1, String value2) throws DataBaseConnectionException;

    Integer calculateActivityNumberWithCreatedByIdCondition(Integer par) throws DataBaseConnectionException;

    Integer calculateActivityByTypeOfActivityAndStatusAccepted(String value) throws DataBaseConnectionException;

    Integer calculateConnectingActivityByUserIdAndStatus(Integer userId, String status) throws DataNotFoundException, DataBaseConnectionException;

    Integer calculateConnectingActivityByUsersBIdAndStatusAndType(Integer userId, String status, String typeActivity) throws DataBaseConnectionException;

    boolean addActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean updateActivityWithoutValidation(Activity activity);

    boolean deleteActivity(Activity activity);
}
