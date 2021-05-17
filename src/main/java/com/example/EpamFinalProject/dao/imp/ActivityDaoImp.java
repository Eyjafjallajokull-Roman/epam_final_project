package com.example.EpamFinalProject.dao.imp;

import com.example.EpamFinalProject.DBManager;
import com.example.EpamFinalProject.constants.Fields;
import com.example.EpamFinalProject.dao.ActivityDao;
import com.example.EpamFinalProject.entity.Activity;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDaoImp implements ActivityDao {
    private static final String FIND_ALL_ACTIVITIES = "SELECT * from activity";
    private static final String CREATE_ACTIVITY = "Insert into activity(start_time,end_time,name,description) values (?,?,?,?)";
    private static final String DELETE_ACTIVITY = "DELETE FROM activity where id = ?";
    private static final String UPDATE_ACTIVITY = "UPDATE activity set start_time = ?, end_time = ?, name = ?, description =? where (id = ?)";

    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public List<Activity> findAllActivity() {
        List<Activity> activities = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ACTIVITIES)) {
            while (resultSet.next()) {
                mapActivity
                        (resultSet, activities);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activities;
    }


    @Override
    public void createActivity(Activity activity) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ACTIVITY)) {
            preparedStatement.setTimestamp(1, activity.getStartTime());
            preparedStatement.setTimestamp(2, activity.getEndTime());
            preparedStatement.setString(3, activity.getName());
            preparedStatement.setString(4, activity.getDescription());
            preparedStatement.execute();

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
    }

    @Override
    public boolean updateActivity(int activityIdToModify, Activity current) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACTIVITY)) {
            preparedStatement.setTimestamp(1, current.getStartTime());
            preparedStatement.setTimestamp(2, current.getEndTime());
            preparedStatement.setString(3, current.getName());
            preparedStatement.setString(4, current.getDescription());
            preparedStatement.setInt(5, activityIdToModify);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteActivity(Activity activity) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACTIVITY)) {
            preparedStatement.setInt(1, activity.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }


    //think about better name
    private void mapActivity(ResultSet resultSet, List<Activity> activities) throws SQLException {
        Activity activity = new Activity();
        activity.setId(resultSet.getInt(Fields.ACTIVITY_ID));
        activity.setStartTime(resultSet.getTimestamp(Fields.ACTIVITY_START_TIME));
        activity.setEndTime(resultSet.getTimestamp(Fields.ACTIVITY_END_TIME));
        activity.setName(resultSet.getString(Fields.ACTIVITY_NAME));
        activity.setDescription(resultSet.getString(Fields.ACTIVITY_DESCRIPTION));
        activity.setTypeOfActivityId(resultSet.getInt(Fields.ACTIVITY_TYPE_OF_ACTIVITY_ID));
        activities.add(activity);
    }
}
