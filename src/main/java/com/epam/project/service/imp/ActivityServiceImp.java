package com.epam.project.service.imp;

import com.epam.project.dao.ActivityDao;
import com.epam.project.dao.DAOFactory;
import com.epam.project.entity.Activity;
import com.epam.project.constants.TypeOfActivity;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActivityServiceImp implements ActivityService {
    private static final Logger log = Logger.getLogger(UserServiceImp.class);
    private static final DAOFactory daoFactory = new DAOFactory();
    private static ActivityDao activityDao;

    @Override
    public List<Activity> findAllActivity() throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllActivity();
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public Activity findActivityById(Integer id) throws NoSuchActivityException {
        Activity activity = new Activity();
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activity = activityDao.findActivityById(id);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activity;
    }

    @Override
    public List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws NoSuchActivityException {
        List<Activity> activities = new ArrayList<>();
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivityByTypeOfActivity(typeOfActivity);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllActivitiesByCreatedId(created_id);
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public boolean addActivity(Activity activity) {
        boolean result;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.createActivity(activity);
            daoFactory.close();
        } catch (DataBaseConnectionException e) {
            log.error(e);
            return false;
        }
        return result;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        boolean result;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.updateActivity(activity);
            daoFactory.close();
        } catch (DataBaseConnectionException e) {
            log.error(e);
            return false;
        }
        return result;
    }

    @Override
    public boolean deleteActivity(Activity activity) {
        boolean result;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.deleteActivity(activity);
            daoFactory.close();
        } catch (DataBaseConnectionException e) {
            log.error(e);
            return false;
        }
        return result;
    }
}
