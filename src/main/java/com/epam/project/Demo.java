package com.epam.project;

import com.epam.project.entity.Activity;
import com.epam.project.entity.Status;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


public class Demo {
    public static void main(String[] args) throws DataNotFoundException, NoUserException, NoSuchActivityException {


        ActivityService activityService = ServiceFactory.getActivityService();
        //all activities with status
        List<Activity> activities = activityService.findAllActivityByStatsOrderWithoutLimit("ACCEPT", "activity.name");

        for (Activity a : activities) {
            if (a.getEndTime() != null) {
                int change = a.getEndTime().compareTo(Timestamp.valueOf(LocalDateTime.now()));
                if (change <= 0) {
                    //якщо пройшов кінцевий час цієї активності вона стає не активна
                    a.setStatus(Status.FINISHED);
                    activityService.updateActivityWithoutValidation(a);
                }
            }
        }
    }


}
