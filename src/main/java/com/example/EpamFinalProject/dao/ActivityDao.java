package com.example.EpamFinalProject.dao;

import com.example.EpamFinalProject.entity.Activity;

import java.util.List;

public interface ActivityDao {

    List<Activity> findAllActivity();

    void createActivity(Activity activity);

    boolean updateActivity(int activityIdToModify, Activity current);

    boolean deleteActivity(Activity activity);
}
