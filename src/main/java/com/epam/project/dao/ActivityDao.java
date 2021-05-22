package com.epam.project.dao;

import com.epam.project.constants.TypeOfActivity;
import com.epam.project.entity.Activity;
import com.epam.project.exception.DataNotFoundException;

import java.util.List;

public interface ActivityDao {

    List<Activity> findAllActivity() throws DataNotFoundException;

    Activity findActivityById(Integer id) throws DataNotFoundException;

    List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws DataNotFoundException;

    List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws DataNotFoundException;

    boolean createActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(Activity activity);
}
