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

    List<Activity> findActivitiesByPaginationParam(Integer id, Integer limit, Integer offset, String orderParam) throws NoSuchActivityException;

    Integer calculateActivityNumber() throws DataBaseConnectionException;

    List<Activity> findAllFromTo(Integer id, Integer limit, Integer offset) throws NoSuchActivityException;

    List<Activity> findAllFromToWithWhereParam(Integer limit, Integer offset, String value1, String value2) throws NoSuchActivityException;

    Integer calculateActivityWithConditionAndWhereParam(String value1, String value2) throws DataBaseConnectionException;

    Integer calculateActivityNumberWithCondition(String par) throws DataBaseConnectionException;

    boolean addActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(Activity activity);
}
