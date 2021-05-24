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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ActivityDaoImp extends GenericAbstractDao<Activity> implements ActivityDao {
    private static final Logger log = Logger.getLogger(ActivityDaoImp.class);
    private static final String SQL_BASE_ORDER = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id where created_by_id=";
    private static final String SQL_BASE_ORDER_PARAM = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id where type_of_activity=? and created_by_id =?";

    private static final String FIND_ALL_ACTIVITIES = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id";
    private static final String FIND_ALL_ACTIVITIES_BY_TYPE = "SELECT * from activity  JOIN activity_status on activity.activity_status_id=activity_status.id where type_of_activity =?";
    private static final String FIND_ACTIVITY_BY_ID = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id where activity.id = ?";
    private static final String CREATE_ACTIVITY = "Insert into activity(start_time,end_time,name,description_en,description_ru,type_of_activity,created_by_id,activity_status_id) values (?,?,?,?,?,?,?,?)";
    private static final String DELETE_ACTIVITY = "DELETE FROM activity where id = ?";
    private static final String UPDATE_ACTIVITY = "UPDATE activity set start_time = ?, end_time = ?, name = ?, description_en =?, " +
            "description_ru = ?,type_of_activity =?, created_by_id =?, activity_status_id =? where (id = ?)";
    private static final String FIND_ACTIVITY_BY_CREATED_USER_ID = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id where created_by_id = ? order by activity.id";
    private static final String FIND_USERS_BY_ACTIVITIES = "Select id from user join user_activity on user_activity.user_id = user.id where user_activity.activity_id = ?;";
    private static final String FIND_ACTIVITY_BY_USER_ID_FIRST_FIVE = "select * from activity join activity_status on activity_status.id = activity.activity_status_id " +
            "left join user_activity on user_activity.activity_id = activity.id where created_by_id = ? or user_activity.user_id = ? limit 5;";

    private static final String Condition_CREATED_BY_ID = " activity where created_by_id =?";
    private static final String Condition_CreatedBy_And_TypeOfActivity_With_Param = " activity where type_of_activity=? and created_by_id =? ;";

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
        return findAll(connection, Activity.class, FIND_ALL_ACTIVITIES);
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
    public Integer calculateActivityNumberWithCondition(String par) throws DataNotFoundException {
        return calculateRowCountsWithCondition(connection,  Condition_CREATED_BY_ID,par );
    }


    @Override
    public List<Activity> findActivitiesByPaginationParam(Integer id, Integer limit, Integer offset, String orderParam) throws DataNotFoundException {
        return findAllFromToWithOrderParam(connection, Activity.class, id, limit, offset, SQL_BASE_ORDER, orderParam);
    }

    @Override
    public Integer calculateActivityNumber() throws DataNotFoundException {
        return calculateRowCounts(connection, "activity");
    }

    @Override
    public Integer calculateActivityWithConditionAndWhereParam(String value1, String value2) throws DataNotFoundException {
        return calculateRowCountsWithConditionAndWhereParam(connection, Condition_CreatedBy_And_TypeOfActivity_With_Param, value1, value2);
    }

    @Override
    public List<Activity> findAllFromTo(Integer id, Integer limit, Integer offset) throws DataNotFoundException {
        return findAllFromTo(connection, Activity.class, id, limit, offset, SQL_BASE_ORDER);
    }

    @Override
    public List<Activity> findAllFromToWithWhereParam(Integer limit, Integer offset, String value1, String value2) throws DataNotFoundException {
        return findAllFromToWithWhereParam(connection, Activity.class, limit, offset, SQL_BASE_ORDER_PARAM, value1, value2);
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


}
