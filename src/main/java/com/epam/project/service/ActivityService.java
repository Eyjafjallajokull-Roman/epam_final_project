package com.epam.project.service;

import com.epam.project.entity.Activity;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;

import java.util.List;

public interface ActivityService {
    List<Activity> findAllActivity() throws NoSuchActivityException;

    Activity findActivityById(Integer id) throws NoSuchActivityException;

    List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws NoSuchActivityException;

    List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws NoSuchActivityException;

    List<Activity> findFirstFiveActivitiesByUserId(Integer id) throws NoUserException;

    List<Activity> findActivitiesByPaginationParam(String value, Integer limit, Integer offset, String orderParam) throws NoSuchActivityException;

    Integer calculateActivityNumber() throws DataBaseConnectionException;

    Integer calculateNumberOfUsersInActivity(String value) throws DataBaseConnectionException;

    Integer calculateActivityNumberByStatusName(String value) throws DataBaseConnectionException;

    List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset) throws NoSuchActivityException;

    List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset) throws NoSuchActivityException;

    List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String value1, String value2) throws NoSuchActivityException;

    List<Activity> findActivitiesByTypeOfActivityAndStatusAccept(String value, Integer limit, Integer offset, String orderParam) throws NoSuchActivityException;

    Integer calculateActivityNumberWithCreatedByIdConditionAndTypeActivity(String value1, String value2) throws DataBaseConnectionException;

    Integer calculateActivityNumberWithCreatedByIdCondition(String par) throws DataBaseConnectionException;

    Integer calculateActivityByTypeOfActivityAndStatusAccepted(String value) throws DataBaseConnectionException;

    boolean addActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean updateActivityWithoutValidation(Activity activity);

    boolean deleteActivity(Activity activity);
}
