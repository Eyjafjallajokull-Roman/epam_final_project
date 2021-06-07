package com.epam.project.constants;

import com.epam.project.dao.imp.UserDaoImp;
import org.apache.log4j.Logger;

public interface Query {
    StringBuilder sb = new StringBuilder();
    /**
     * User Query
     */
    String FIND_ALL_USERS = "SELECT * from user ";
    String FIND_ALL_USERS_ORDER = "SELECT * from user where role = 'CLIENT' ORDER BY  ";

    String FIND_USERS_BY_RolE = "SELECT * from user where role = ?";
    String CREATE_USER = "Insert into user(email,password,name,surname,role) values (?,?,?,?,?)";
    String ADD_USER_TO_ACTIVITY = "Insert into user_activity(activity_id,user_id)  values (?,?)";
    String DELETE_USER = "DELETE FROM user where id = ?";
    String UPDATE_USER = "UPDATE user set email = ?, password = ?, name = ?, surname =?, role =? " +
            "where (id = ?)";
    String FIND_USER_BY_EMAIL = "SELECT * from user where email = ?;";
    String FIND_USER_BY_ID = "SELECT * from user where user.id = ?;";

    String CHECK_IF_USER_IN_ACTIVITY = " user_activity where activity_id = ?  and user_id = ? ;";
    String FIND_ACTIVITIES_BY_USER = "Select id from activity join user_activity on user_activity.activity_id = activity.id where user_activity.user_id = ?;";
    String FIND_ALL_CONNECTING_USERS_BY_ACTIVITY = "Select user_id from user_activity join activity on activity.id = user_activity.activity_id " +
            "join user on user_id = user.id where activity_id = ? order by ";
    String COUNT__ALL_CONNECTING_USERS_BY_ACTIVITY = " user_activity join activity on activity.id = user_activity.activity_id where activity_id = ? ";

    String DELETE_USER_FROM_ACTIVITY = "DELETE FROM user_activity where activity_id = ? and user_id = ?";


    /**
     * All activities query
     */

    String SQL_BASE = "SELECT * from activity JOIN activity_status on activity.activity_status_id=activity_status.id ";
    //Select
    String FIND_ALL_ACTIVITIES_BY_CREATED_USERS_ID_AND_EXISTING = sb.append(" select * from activity").append("  join activity_status on activity_status.id = activity.activity_status_id ")
            .append(" where activity.id in (select ac2.id from activity ac2 ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join activity_status on activity_status.id = ac2.activity_status_id ")
            .append(" where ua.user_id = ? and activity_status.name = 'ACCEPT') or activity.id in ")
            .append(" (select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id ")
            .append(" join activity_status  on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' where u.id = ?); ").toString();
    String FIND_ALL_ACTIVITIES_BY_CREATED_USER_ID_AND_TYPE = sb.append(" select * from activity").append("  join activity_status on activity_status.id = activity.activity_status_id ")
            .append(" where activity.id in (select ac2.id from activity ac2 ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join activity_status on activity_status.id = ac2.activity_status_id ")
            .append(" where ua.user_id = ? and activity_status.name = 'ACCEPT' and ac2.type_of_activity = ?) or activity.id in ")
            .append(" (select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id ")
            .append(" join activity_status  on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' and ac3.type_of_activity=? where u.id = ?); ").toString();

    String FIND_ALL_ACTIVITIES_BY_STATUS_NAME = SQL_BASE + " where activity_status.name=? order by ";
    String FIND_ALL_ACTIVITIES_BY_TYPE = SQL_BASE + " where type_of_activity =? and activity_status.name ='ACCEPT'";
    String FIND_ACTIVITY_BY_ID = SQL_BASE + " where activity.id = ?";
    String FIND_ACTIVITY_BY_CREATED_USER_ID = SQL_BASE + " where created_by_id = ? and activity_status.name ='ACCEPT' order by start_time limit 5 ";
    String FIND_ALL_ACTIVITIES_BY_TYPE_OF_ACTIVITY_AND_ORDER_PARAM = SQL_BASE + "where type_of_activity =? and activity_status.name ='ACCEPT' order by ";
    String FIND_USERS_BY_ACTIVITIES = "Select id from user join user_activity on user_activity.user_id = user.id where user_activity.activity_id = ?;";
    String FIND_ACTIVITY_BY_USER_ID_FIRST_FIVE = SQL_BASE + " join user_activity on user_activity.activity_id = activity.id where created_by_id = ? or user_activity.user_id = ? and activity_status.name = 'ACCEPT' order by start_time  limit 5;";
    String FIND_ALL_CONNECTING_ACTIVITY_BY_USER = "Select activity_id from user_activity join user on user.id = user_activity.user_id where user.email = ? ";

    String FIND_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS_AND_TYPE_ACTIVITY = "SELECT activity_id from activity JOIN activity_status on activity.activity_status_id=activity_status.id join user_activity on user_activity.activity_id = activity.id where user_id  = ?  and activity_status.name = ? and type_of_activity = ? order by ";
    String FIND_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS = "SELECT activity_id from activity JOIN activity_status on activity.activity_status_id=activity_status.id join user_activity on user_activity.activity_id = activity.id where user_id  = ? and activity_status.name = ?  order by ";
    String FIND_ALL_ACTIVITIES_CREATED_BY_ID = SQL_BASE + " where created_by_id = ? and activity_status.name = ? order by ";
    //crd
    String CREATE_ACTIVITY = "INSERT INTO activity(start_time,end_time,name,description_en,description_ru,type_of_activity,created_by_id,activity_status_id,activity_old_id) values (?,?,?,?,?,?,?,?,?)";
    String DELETE_ACTIVITY = "DELETE FROM activity where id = ?";
    String UPDATE_ACTIVITY = "UPDATE activity set start_time = ?, end_time = ?, name = ?, description_en =?, " +
            "description_ru = ?,type_of_activity =?, created_by_id =?, activity_status_id =? , activity_old_id = ? where (id = ?)";
    String CREATE_ACTIVITY_WITH_OLD_ACTIVITY = "INSERT INTO activity(start_time,end_time,name,description_en,description_ru,type_of_activity,created_by_id,activity_status_id) values (?,?,?,?,?,?,?,?)";
    //cond

    String Condition_CREATED_BY_ID = sb.append(" activity").append("  join activity_status on activity_status.id = activity.activity_status_id ")
            .append(" where activity.id in (select ac2.id from activity ac2 ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join activity_status on activity_status.id = ac2.activity_status_id ")
            .append(" where ua.user_id = ? and activity_status.name = 'ACCEPT') or activity.id in ")
            .append(" (select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id ")
            .append(" join activity_status  on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' where u.id = ?); ").toString();

    String Condition_CreatedBy_And_TypeOfActivity_With_Param = sb.append(" activity").append("  join activity_status on activity_status.id = activity.activity_status_id ")
            .append(" where activity.id in (select ac2.id from activity ac2 ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join user_activity ua on ac2.id = ua.activity_id ")
            .append(" join activity_status on activity_status.id = ac2.activity_status_id ")
            .append(" where ua.user_id = ? and activity_status.name = 'ACCEPT' and ac2.type_of_activity = ?) or activity.id in ")
            .append(" (select ac3.id from activity ac3 join user u on u.id = ac3.created_by_id ")
            .append(" join activity_status  on activity_status.id = ac3.activity_status_id and activity_status.name = 'ACCEPT' and ac3.type_of_activity=? where u.id = ?); ").toString();


    String Condition_TypeOfActivity_With_Param = " activity JOIN activity_status on activity.activity_status_id=activity_status.id where type_of_activity=?  and  activity_status.name ='ACCEPT' ;";
    String CONDITION_STATUS_NAME = " activity JOIN activity_status on activity.activity_status_id=activity_status.id where activity_status.name = ? ";
    String CALCULATE_ALL_CONNECTING_ACTIVITY_BY_USER = "user_activity join user on user.id = user_activity.user_id where user.email = ?";
    String CALCULATE_ACTIVITY_BY_CREATED_ID = " activity JOIN activity_status on activity.activity_status_id=activity_status.id where created_by_id = ? and activity_status.name = ?";

    String CALCULATE_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS_AND_TYPE_ACTIVITY = " activity JOIN activity_status on activity.activity_status_id=activity_status.id  " +
            "join user_activity on user_activity.activity_id = activity.id where user_id  = ?  and activity_status.name = ? and type_of_activity = ? ";
    String CALCULATE_ALL_ACTIVITIES_BY_CONNECTING_USER_AND_STATUS = " activity JOIN activity_status on activity.activity_status_id=activity_status.id " +
            "join user_activity on user_activity.activity_id = activity.id where user_id  = ?  and activity_status.name = ? ";
    String FIND_HOW_MANY_USERS_IN_ACTIVITY = "select count(*) from user_activity where activity_id = ?";
}
