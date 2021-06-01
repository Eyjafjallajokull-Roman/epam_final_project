package com.epam.project.dao.imp;

import com.epam.project.entity.Status;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.constants.Fields;
import com.epam.project.dao.ActivityDao;
import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.Mapper;
import com.epam.project.entity.Activity;
import com.epam.project.exception.DataNotFoundException;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ActivityDaoImp extends GenericAbstractDao<Activity> implements ActivityDao {
    private static final Logger log = Logger.getLogger(ActivityDaoImp.class);
    private static final String SQL_BASE = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id ";

    //Select
    private static final String FIND_ALL_ACTIVITIES_BY_CREATED_USERS_ID_AND_EXISTING = " select * from activity " +
            "join activity_status on activity_status.id = activity.activity_status_id " +
            " where activity.id in (select ac2.id from activity ac2 " +
            "  join user_activity ua on ac2.id = ua.activity_id " +
            "  join activity_status  on activity_status.id = ac2.activity_status_id " +
            "    where ua.user_id = ? and activity_status.name = 'ACCEPT') or activity.id in " +
            "(select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id " +
            " join activity_status on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' where u.id = ?) order by "; //2 param
    private static final String FIND_ALL_ACTIVITIES_BY_CREATED_USER_ID_AND_TYPE = " select * from activity " +
            " join activity_status on activity_status.id = activity.activity_status_id" +
            " where activity.id in (select ac2.id from activity ac2 " +
            " join user_activity ua on ac2.id = ua.activity_id " +
            " join activity_status on activity_status.id = ac2.activity_status_id " +
            "  where ua.user_id = ? and activity_status.name = 'ACCEPT' and ac2.type_of_activity = ?) or activity.id in " +
            " (select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id " +
            " join activity_status on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' and ac3.type_of_activity=? where u.id = ?) order by ";
    private static final String FIND_ALL_ACTIVITIES_BY_STATUS_NAME = SQL_BASE + " where activity_status.name=? order by ";
    private static final String FIND_ALL_ACTIVITIES_BY_TYPE = SQL_BASE + " where type_of_activity =? and activity_status.name ='ACCEPT'";
    private static final String FIND_ACTIVITY_BY_ID = SQL_BASE + " where activity.id = ?";
    private static final String FIND_ACTIVITY_BY_CREATED_USER_ID = SQL_BASE + " where created_by_id = ? and activity_status.name ='ACCEPT' order by start_time limit 5 ";
    private static final String FIND_ALL_ACTIVITIES_BY_TYPE_OF_ACTIVITY_AND_ORDER_PARAM = SQL_BASE + "where type_of_activity =? and activity_status.name ='ACCEPT' order by ";
    private static final String FIND_USERS_BY_ACTIVITIES = "Select id from user join user_activity on user_activity.user_id = user.id where user_activity.activity_id = ?;";
    private static final String FIND_ACTIVITY_BY_USER_ID_FIRST_FIVE = SQL_BASE +
            " join user_activity on user_activity.activity_id = activity.id where created_by_id = ? or user_activity.user_id = ? and activity_status.name = 'Accept' order by start_time  limit 5;";

    private static final String FIND_ALL_CONNECTING_ACTIVITY_BY_USER = "Select activity_id from user_activity join user on user.id = user_activity.user_id where user.email = ? ";

    private static final String FIND_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS_AND_TYPE_ACTIVITY = "SELECT activity_id from activity JOIN activity_status on activity.activity_status_id=activity_status.id join user_activity on user_activity.activity_id = activity.id where user_id  = ?  and activity_status.name = ? and type_of_activity = ? order by ";
    private static final String FIND_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS = "SELECT activity_id from activity JOIN activity_status on activity.activity_status_id=activity_status.id join user_activity on user_activity.activity_id = activity.id where user_id  = ? and activity_status.name = ?  order by ";
    private static final String FIND_ALL_ACTIVITIES_CREATED_BY_ID = SQL_BASE + " where created_by_id = ? and activity_status.name = ? order by ";
    //crd
    private static final String CREATE_ACTIVITY = "INSERT INTO activity(start_time,end_time,name,description_en,description_ru,type_of_activity,created_by_id,activity_status_id) values (?,?,?,?,?,?,?,?)";
    private static final String DELETE_ACTIVITY = "DELETE FROM activity where id = ?";
    private static final String UPDATE_ACTIVITY = "UPDATE activity set start_time = ?, end_time = ?, name = ?, description_en =?, " +
            "description_ru = ?,type_of_activity =?, created_by_id =?, activity_status_id =? where (id = ?)";
    //cond

    private static final String Condition_CREATED_BY_ID = " activity " +
            "join activity_status on activity_status.id = activity.activity_status_id " +
            "  where activity.id in (select ac2.id from activity ac2 " +
            "  join user_activity ua on ac2.id = ua.activity_id " +
            "  join activity_status on activity_status.id = ac2.activity_status_id " +
            "  where ua.user_id = ? and activity_status.name = 'ACCEPT') or activity.id in " +
            " (select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id " +
            " join activity_status on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' where u.id = ?) ;";
    private static final String Condition_CreatedBy_And_TypeOfActivity_With_Param = "  activity " +
            "  join activity_status on activity_status.id = activity.activity_status_id " +
            "  where activity.id in (select ac2.id from activity ac2 " +
            "  join user_activity ua on ac2.id = ua.activity_id " +
            "  join activity_status on activity_status.id = ac2.activity_status_id " +
            "  where ua.user_id = ? and activity_status.name = 'ACCEPT' and ac2.type_of_activity = ?) or activity.id in " +
            "  (select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id " +
            "  join activity_status  on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' and ac3.type_of_activity=? where u.id = ?); ";

    private static final String Condition_TypeOfActivity_With_Param = " activity JOIN activity_status on activity.activity_status_id=activity_status.id where type_of_activity=?  and  activity_status.name ='ACCEPT' ;";
    private static final String CONDITION_STATUS_NAME = " activity JOIN activity_status on activity.activity_status_id=activity_status.id where activity_status.name = ? ";
    private static final String CALCULATE_ALL_CONNECTING_ACTIVITY_BY_USER = "user_activity join user on user.id = user_activity.user_id where user.email = ?";
    private static final String CALCULATE_ACTIVITY_BY_CREATED_ID = " activity JOIN activity_status on activity.activity_status_id=activity_status.id where created_by_id = ? and activity_status.name = ?";

    private static final String CALCULATE_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS_AND_TYPE_ACTIVITY = " activity JOIN activity_status on activity.activity_status_id=activity_status.id  " +
            "join user_activity on user_activity.activity_id = activity.id where user_id  = ?  and activity_status.name = ? and type_of_activity = ? ";
    private static final String CALCULATE_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS = " activity JOIN activity_status on activity.activity_status_id=activity_status.id " +
            "join user_activity on user_activity.activity_id = activity.id where user_id  = ?  and activity_status.name = ? ";
    private static final String FIND_HOW_MANY_USERS_IN_ACTIVITY = "select count(*) from user_activity where activity_id = ?";


    private final Mapper<Activity, PreparedStatement> mapperToDB = (Activity activity, PreparedStatement preparedStatement) -> {
        preparedStatement.setTimestamp(1, activity.getStartTime());
        preparedStatement.setTimestamp(2, activity.getEndTime());
        preparedStatement.setString(3, activity.getName());
        preparedStatement.setString(4, activity.getDescriptionEng());
        preparedStatement.setString(5, activity.getDescriptionRus());
        preparedStatement.setString(6, activity.getTypeOfActivity().toString());
        preparedStatement.setInt(7, activity.getCreatedByUserID());
        preparedStatement.setInt(8, activity.getStatus().ordinal());
    };

    private final Mapper<ResultSet, Activity> mapperFromDB = (ResultSet resultSet, Activity activity) -> {
        activity.setId(resultSet.getInt(Fields.ACTIVITY_ID));
        activity.setStartTime(resultSet.getTimestamp(Fields.ACTIVITY_START_TIME));
        activity.setEndTime(resultSet.getTimestamp(Fields.ACTIVITY_END_TIME));
        activity.setName(resultSet.getString(Fields.ACTIVITY_NAME));
        activity.setDescriptionEng(resultSet.getString(Fields.ACTIVITY_DESCRIPTION_En));
        activity.setDescriptionRus(resultSet.getString(Fields.ACTIVITY_DESCRIPTION_RUS));
        //create field
        activity.setTypeOfActivity(TypeOfActivity.valueOf(resultSet.getString(Fields.ACTIVITY_TYPE_OF_ACTIVITY)));
        activity.setCreatedByUserID(resultSet.getInt(Fields.ACTIVITY_CREATED_BY_USER));
        activity.setStatus(Status.valueOf(resultSet.getString(Fields.STATUS_NAME)));
    };


    private final Connection connection;

    public ActivityDaoImp(Connection connection) {
        this.connection = connection;
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<Activity> findAllActivity() throws DataNotFoundException {
        return findAll(connection, Activity.class, SQL_BASE);
    }

    @Override
    public List<Activity> findAllActivitiesByCreatedId(Integer created_id) throws DataNotFoundException {
        return findAsListBy(connection, Activity.class, FIND_ACTIVITY_BY_CREATED_USER_ID, created_id);
    }


    @Override
    public List<Activity> findFirstFiveActivitiesByUserId(Integer userId) throws DataNotFoundException {
        return findAsListByTwoParam(connection, Activity.class, FIND_ACTIVITY_BY_USER_ID_FIRST_FIVE, userId);
    }

    @Override
    public Integer calculateActivityNumberWithCreatedByIdCondition(Integer par) throws DataNotFoundException {
        return calculateRowCountsWithConditionAndWhereParam(connection, Condition_CREATED_BY_ID, par, par);
    }


    @Override
    public List<Activity> findActivitiesByStatusName(String value, Integer limit, Integer offset, String order) throws DataNotFoundException {
        return findAllFromToWithValue(connection, Activity.class, value, limit, offset, order, FIND_ALL_ACTIVITIES_BY_STATUS_NAME);
    }

    @Override
    public List<Activity> findActivitiesByTypeOfActivityAndStatusAccept(String value, Integer limit, Integer offset, String orderParam) throws DataNotFoundException {
        return findAllFromToWithValue(connection, Activity.class, value, limit, offset, orderParam, FIND_ALL_ACTIVITIES_BY_TYPE_OF_ACTIVITY_AND_ORDER_PARAM);
    }


    @Override
    public Integer calculateActivityNumber() throws DataNotFoundException {
        return calculateRowCounts(connection, "activity");
    }


    @Override
    public Integer calculateActivityNumberByStatusName(String value) throws DataNotFoundException {
        return calculateRowCountsWithCondition(connection, CONDITION_STATUS_NAME, value);
    }

    @Override
    public Integer calculateActivityByCreatedAndTypeActivityCondition(Integer userId, String typeActivity) throws DataNotFoundException {
        return calculateRowCountsWithConditionWithFourParam(connection, Condition_CreatedBy_And_TypeOfActivity_With_Param, userId, typeActivity, userId);
    }

    @Override
    public Integer calculateActivityByTypeOfActivityAndStatusAccepted(String value) throws DataNotFoundException {
        return calculateRowCountsWithCondition(connection, Condition_TypeOfActivity_With_Param, value);
    }

    @Override
    public Integer calculateNumberOfUsersInActivity(String value) throws DataNotFoundException {
        return calculateRowCountsWithCondition(connection, FIND_HOW_MANY_USERS_IN_ACTIVITY, value);
    }

    @Override
    public Integer calculateConnectingActivityByUserIdAndStatus(Integer userId, String status) throws DataNotFoundException {
        return calculateRowCountsWithConditionAndWhereParam(connection, CALCULATE_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS, String.valueOf(userId), status);
    }

    @Override
    public Integer calculateConnectingActivityByUsersIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity) throws DataNotFoundException {
        return calculateRowCountsWithConditionWithThreeParam(connection, CALCULATE_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS_AND_TYPE_ACTIVITY, String.valueOf(userId), status, typeActivity);
    }

    @Override
    public Integer calculateActivityByCreatedId(Integer userId, String status) throws DataNotFoundException {
        return calculateRowCountsWithConditionAndWhereParam(connection, CALCULATE_ACTIVITY_BY_CREATED_ID, userId, status);
    }

    @Override
    public List<Activity> findAllActivitiesByCreatedId(Integer id, String order, Integer limit, Integer offset, String status) throws DataNotFoundException {
        return findAllFromToWithWhereParam(connection, Activity.class, limit, offset, FIND_ALL_ACTIVITIES_CREATED_BY_ID,id,status,order);
    }

    @Override
    public List<Activity> findActivitiesWhereCreatedIdWithLimit(String value, Integer limit, Integer offset, String order) throws DataNotFoundException {
        return findAllFromToWithWhereParam(connection, Activity.class, limit, offset, FIND_ALL_ACTIVITIES_BY_CREATED_USERS_ID_AND_EXISTING, value, value, order);
    }

    @Override
    public List<Activity> findAllActivityByCreatedIdAndTypeActivity(Integer limit, Integer offset, String userId, String typeActivity, String order) throws DataNotFoundException {
        return findAllFromToActivitiesAllUsers(connection, Activity.class, limit, offset, FIND_ALL_ACTIVITIES_BY_CREATED_USER_ID_AND_TYPE, userId, typeActivity, typeActivity, userId, order);
    }


    @Override
    public Activity findActivityById(Integer id) throws DataNotFoundException {
        return findBy(connection, Activity.class, FIND_ACTIVITY_BY_ID, id);
    }


    @Override
    public List<Activity> findActivityByTypeOfActivity(TypeOfActivity typeOfActivity) throws DataNotFoundException {
        return findAsListBy(connection, Activity.class, FIND_ALL_ACTIVITIES_BY_TYPE, typeOfActivity.toString());
    }

    @Override
    public boolean createActivity(Activity activity) {
        return addToDB(connection, activity, CREATE_ACTIVITY);
    }

    @Override
    public boolean updateActivity(Activity activity) {
        return updateInDB(connection, activity, UPDATE_ACTIVITY, 9, activity.getId());
    }

    @Override
    public boolean deleteActivity(Activity activity) {
        return deleteFromDB(connection, DELETE_ACTIVITY, activity.getId());
    }


    @Override
    public Set<Integer> addUsersToActivities(Activity activity) throws DataNotFoundException {
        Set<Integer> usersSet = new TreeSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERS_BY_ACTIVITIES)) {
            preparedStatement.setInt(1, activity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                usersSet.add(resultSet.getInt(1));
            }
            activity.setUsersId(usersSet);
            return usersSet;
        } catch (SQLException throwables) {
            log.error(throwables);
            throw new DataNotFoundException();
        }
    }

    @Override
    public List<Activity> findAllConnectingActivityByUserIdAndStatus(Integer userId, String status, Integer limit, Integer offset, String order) throws DataNotFoundException {
        List<Activity> activities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS + order + " limit " + limit + " , " + offset + ";")) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activities.add(findActivityById(resultSet.getInt(1)));
            }
        } catch (SQLException throwables) {
            log.error(throwables);
            throw new DataNotFoundException();
        }
        return activities;
    }


    @Override
    public List<Activity> findAllConnectingActivityByUserIdAndStatusAndTypeActivity(Integer userId, String status, String typeActivity, Integer limit, Integer offset, String order) throws DataNotFoundException {
        List<Activity> activities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS_AND_TYPE_ACTIVITY + order + " limit " + limit + " , " + offset + ";")) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, status);
            preparedStatement.setString(3, typeActivity);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activities.add(findActivityById(resultSet.getInt(1)));
            }
        } catch (SQLException throwables) {
            log.error(throwables);
            throw new DataNotFoundException();
        }
        return activities;
    }


}


