package com.epam.project.dao.imp;

import com.epam.project.constants.Status;
import com.epam.project.constants.TypeOfActivity;
import com.epam.project.constants.Fields;
import com.epam.project.dao.ActivityDao;
import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.Mapper;
import com.epam.project.entity.Activity;
import com.epam.project.exception.DataNotFoundException;


import java.sql.*;
import java.util.List;

public class ActivityDaoImp extends GenericAbstractDao<Activity> implements ActivityDao {
    //updated
    private static final String FIND_ALL_ACTIVITIES = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id";
    private static final String FIND_ALL_ACTIVITIES_BY_TYPE = "SELECT * from activity where type_of_activity =?";
    private static final String FIND_ACTIVITY_BY_ID = "SELECT * from activity where id = ?";
    //updated
    private static final String CREATE_ACTIVITY = "Insert into activity(start_time,end_time,name,description,type_of_activity,created_by_id) values (?,?,?,?,?,?,?)";
    private static final String DELETE_ACTIVITY = "DELETE FROM activity where id = ?";
    private static final String UPDATE_ACTIVITY = "UPDATE activity set start_time = ?, end_time = ?, name = ?, description =?, type_of_activity =?, status =? where (id = ?)";

    private final Mapper<Activity, PreparedStatement> mapperToDB = (Activity activity, PreparedStatement preparedStatement) -> {
        preparedStatement.setTimestamp(1, activity.getStartTime());
        preparedStatement.setTimestamp(2, activity.getEndTime());
        preparedStatement.setString(3, activity.getName());
        preparedStatement.setString(4, activity.getDescription());
        preparedStatement.setInt(5, activity.getTypeOfActivity().ordinal());
        preparedStatement.setInt(6, activity.getUserID());
        preparedStatement.setInt(7, activity.getStatus().ordinal());
    };

    private final Mapper<ResultSet, Activity> mapperFromDB = (ResultSet resultSet, Activity activity) -> {
        activity.setId(resultSet.getInt(Fields.ACTIVITY_ID));
        activity.setStartTime(resultSet.getTimestamp(Fields.ACTIVITY_START_TIME));
        activity.setEndTime(resultSet.getTimestamp(Fields.ACTIVITY_END_TIME));
        activity.setName(resultSet.getString(Fields.ACTIVITY_NAME));
        activity.setDescription(resultSet.getString(Fields.ACTIVITY_DESCRIPTION));
        //create field
        activity.setTypeOfActivity(TypeOfActivity.valueOf(resultSet.getString(Fields.ACTIVITY_TYPE_OF_ACTIVITY)));
        activity.setUserID(resultSet.getInt(Fields.ACTIVITY_CREATED_BY_USER));
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
        return updateInDB(connection, activity, UPDATE_ACTIVITY, 7, activity.getId());
    }

    @Override
    public boolean deleteActivity(Activity activity) {
        return deleteFromDB(connection, DELETE_ACTIVITY, activity.getId());
    }
}
