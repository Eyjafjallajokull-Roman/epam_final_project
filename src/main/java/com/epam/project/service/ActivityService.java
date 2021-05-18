package com.epam.project.service;

import com.epam.project.entity.Activity;
import com.epam.project.constants.TypeOfActivity;
import com.epam.project.exception.NoSuchActivityException;

import java.util.List;

public interface ActivityService {
    List<Activity> findAllActivity() throws NoSuchActivityException;

    Activity findActivityById(Integer id) throws NoSuchActivityException;

    List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws NoSuchActivityException;

    boolean addActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(Activity activity);
}
