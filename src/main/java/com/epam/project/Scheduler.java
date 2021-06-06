package com.epam.project;

import com.epam.project.command.imp.AddNewActivityCommand;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Status;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.Time;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler implements Runnable {
    private static final Logger log = Logger.getLogger(Scheduler.class);
    private final ActivityService activityService = ServiceFactory.getActivityService();

    @Override
    public void run() {
        try {
            ActivityService activityService = ServiceFactory.getActivityService();
            //all activities with status
            List<Activity> activities = activityService.findAllActivityByStatsOrderWithoutLimit(Status.ACCEPT.name(), "activity.name");

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
        } catch (NoSuchActivityException e) {
            log.error(e);
        }
    }
}

