package com.epam.project.service.imp;

import com.epam.project.dao.ActivityDao;
import com.epam.project.dao.DAOFactory;
import com.epam.project.entity.Activity;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
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
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
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
            activityDao.addUsersToActivities(activity);
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
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
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
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
            daoFactory.close();
        } catch (DataBaseConnectionException | DataNotFoundException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public List<Activity> findFirstFiveActivitiesByUserId(Integer id) throws NoUserException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findFirstFiveActivitiesByUserId(id);
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoUserException();
        }
        return activities;
    }

    @Override
    public List<Activity> findActivitiesByPaginationParam(String value, Integer limit, Integer offset, String orderParam) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivitiesByPaginationParam(value, limit, offset, orderParam);
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public Integer calculateActivityNumber() throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityNumber();
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public Integer calculateNumberOfUsersInActivity(String value) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateNumberOfUsersInActivity(value);
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public Integer calculateActivityNumberByStatusName(String value) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityNumberByStatusName(value);
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivitiesByStatusName(value, limit, offset);
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivitiesWhereCreatedIdWithLimit(value, limit, offset);
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String value1, String value2) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllActivityByCreatedIdAndTypeActivity(limit, offset, value1, value2);
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public List<Activity> findActivitiesByTypeOfActivityAndStatusAccept(String value, Integer limit, Integer offset, String orderParam) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivitiesByTypeOfActivityAndStatusAccept(value, limit, offset, orderParam);
            for (Activity activity : activities) {
                activityDao.addUsersToActivities(activity);
            }
            daoFactory.close();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            throw new NoSuchActivityException();
        }
        return activities;
    }

    @Override
    public Integer calculateActivityNumberWithCreatedByIdConditionAndTypeActivity(String value1, String value2) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityByCreatedAndTypeActivityCondition(value1, value2);
            daoFactory.commitTransaction();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            daoFactory.rollbackTransaction();
        }
        return result;
    }


    @Override
    public Integer calculateActivityNumberWithCreatedByIdCondition(String par) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityNumberWithCreatedByIdCondition(par);
            daoFactory.commitTransaction();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public Integer calculateActivityByTypeOfActivityAndStatusAccepted(String value) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityByTypeOfActivityAndStatusAccepted(value);
            daoFactory.commitTransaction();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            daoFactory.rollbackTransaction();
        }
        return result;
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
