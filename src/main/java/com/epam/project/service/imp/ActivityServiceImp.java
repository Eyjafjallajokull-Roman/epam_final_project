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

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        Activity activity;
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
        List<Activity> activities;
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
    public List<Activity> findAllActivityByTypeOfActivityAndStatusOrderWithoutLimit(String typeOfActivity, String order) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllActivityByTypeOfActivityAndStatusOrderWithoutLimit(typeOfActivity, order);
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
    public List<Activity> findAllActivityByStatsOrderWithoutLimit(String status, String order) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllActivityByStatsOrderWithoutLimit(status, order);
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
    public Integer calculateActivityByCreatedId(Integer userId, String status) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityByCreatedId(userId, status);
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public List<Activity> findAllActivitiesByCreatedId(Integer id, String order, Integer limit, Integer offset, String status) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllActivitiesByCreatedId(id, order, limit, offset, status);
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
    public List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset, String order) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivitiesByStatusName(value, limit, offset, order);
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
    public List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset, String order) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivitiesWhereCreatedIdWithLimit(value, limit, offset, order);
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
    public List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String userId, String typeOfActivity, String order) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllActivityByCreatedIdAndTypeActivity(limit, offset, userId, typeOfActivity, order);
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
    public List<Activity> findActivitiesWhereCreatedIdWithoutLimits(Integer id, String order) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findActivitiesWhereCreatedIdWithoutLimits(id, order);
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
    public List<Activity> findAllConnectingActivityByUserIdAndStatus(Integer userId, String status, Integer limit, Integer offset, String order) throws DataNotFoundException, NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllConnectingActivityByUserIdAndStatus(userId, status, limit, offset, order);
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
    public List<Activity> findAllConnectingActivityByUserIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity, Integer limit, Integer offset, String order) throws NoSuchActivityException {
        List<Activity> activities;
        try {
            daoFactory.open();
            activityDao = daoFactory.getActivityDao();
            activities = activityDao.findAllConnectingActivityByUserIdAndStatusAndTypeActivity(userId, status, typeActivity, limit, offset, order);
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
    public Integer calculateActivityNumberWithCreatedByIdConditionAndTypeActivity(Integer userId, String typeOfActivity) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityByCreatedAndTypeActivityCondition(userId, typeOfActivity);
            daoFactory.commitTransaction();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            daoFactory.rollbackTransaction();
        }
        return result;
    }


    @Override
    public Integer calculateActivityNumberWithCreatedByIdCondition(Integer par) throws DataBaseConnectionException {
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
    public Integer calculateActivityByTypeOfActivityAndStatusAccepted(String typeOfActivity) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateActivityByTypeOfActivityAndStatusAccepted(typeOfActivity);
            daoFactory.commitTransaction();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public Integer calculateConnectingActivityByUserIdAndStatus(Integer userId, String status) throws DataNotFoundException, DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateConnectingActivityByUserIdAndStatus(userId, status);
            daoFactory.commitTransaction();
        } catch (DataNotFoundException | DataBaseConnectionException e) {
            log.error(e);
            daoFactory.rollbackTransaction();
        }
        return result;
    }

    @Override
    public Integer calculateConnectingActivityByUsersBIdAndStatusAndType(Integer userId, String status, String typeActivity) throws DataBaseConnectionException {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            activityDao = daoFactory.getActivityDao();
            result = activityDao.calculateConnectingActivityByUsersIdAndStatusAndTypeActivity(userId, status, typeActivity);
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
            if (activity.getTypeOfActivity().equals(TypeOfActivity.REMINDER)) {
                result = validateReminder(activity) && activityDao.createActivity(activity);
            } else if (activity.getTypeOfActivity().equals(TypeOfActivity.TIME_TRACKER)) {
                result = activityDao.createActivity(activity);
            } else {
                result = validateActivity(activity) && activityDao.createActivity(activity);
            }
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
            result = validateActivity(activity) && activityDao.updateActivity(activity);
            daoFactory.close();
        } catch (DataBaseConnectionException e) {
            log.error(e);
            return false;
        }
        return result;
    }

    @Override
    public boolean updateActivityWithoutValidation(Activity activity) {
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

    private boolean validateReminder(Activity activity) {
        Timestamp endTs = activity.getEndTime();
        Timestamp nowTs = Timestamp.valueOf(LocalDateTime.now());
        int t1 = endTs.compareTo(nowTs);
        return t1 > 0;
    }

    private boolean validateActivity(Activity activity) {
        Timestamp endTs = activity.getEndTime();
        Timestamp startTs = activity.getStartTime();
        Timestamp nowTs = Timestamp.valueOf(LocalDateTime.now());

        //compares ts1 with ts2
        int b3 = endTs.compareTo(startTs);
        int b2 = startTs.compareTo(nowTs);
        return b3 > 0 && b2 > 0;

    }
}
