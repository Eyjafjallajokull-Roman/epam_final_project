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

    List<Activity> findAllFromTo(Integer id, Integer limit, Integer offset) throws DataNotFoundException;

    List<Activity> findAllFromToWithWhereParam(Integer limit, Integer offset, String value1, String value2) throws DataNotFoundException;

    List<Activity> findActivitiesByPaginationParam(Integer id, Integer limit, Integer offset, String orderParam) throws DataNotFoundException;


    //calculate
    Integer calculateActivityNumberWithCondition(String value) throws DataNotFoundException;

    Integer calculateActivityNumber() throws DataNotFoundException;

    Integer calculateActivityWithConditionAndWhereParam(String value1, String value2) throws DataNotFoundException;
    //crud

    Set<Integer> addUsersToActivities(Activity activity) throws DataNotFoundException;

    boolean createActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(Activity activity);
}
