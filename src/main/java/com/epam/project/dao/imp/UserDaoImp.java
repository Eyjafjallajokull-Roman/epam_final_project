package com.epam.project.dao.imp;

import com.epam.project.constants.Query;
import com.epam.project.dao.UserDao;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.constants.Fields;
import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.Mapper;
import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UserDaoImp extends GenericAbstractDao<User> implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImp.class);
    private final Connection connection;

    private final Mapper<User, PreparedStatement> mapperToDB = (User user, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getSurname());
        // 1 - roleId = "client"
        preparedStatement.setString(5, user.getRole().toString());
    };
    private final Mapper<ResultSet, User> mapperFromDB = (ResultSet rs, User user) -> {
        user.setId(rs.getInt(Fields.USER_ID));
        user.setEmail(rs.getString(Fields.USER_EMAIL));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setName(rs.getString(Fields.USER_NAME));
        user.setSurname(rs.getString(Fields.USER_SURNAME));
        //create field
        user.setRole(Role.valueOf(rs.getString(Fields.USER_ROLE)));
    };


    public UserDaoImp(Connection connection) {
        this.connection = connection;
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<User> findAllUsers() throws DataNotFoundException {
        return findAll(connection, User.class, Query.FIND_ALL_USERS);
    }


    @Override
    public User findUserById(Integer id) throws DataNotFoundException {
        return findBy(connection, User.class, Query.FIND_USER_BY_ID, id);
    }

    @Override
    public User findUserByLogin(String login) throws DataNotFoundException {
        return findBy(connection, User.class, Query.FIND_USER_BY_EMAIL, login);

    }

    @Override
    public List<User> findAllConnectingUsersByActivity(Integer id, Integer limit, Integer offset, String order) throws DataNotFoundException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_ALL_CONNECTING_USERS_BY_ACTIVITY + order + " limit " + limit + " , " + offset + ";")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(findUserById(resultSet.getInt(1)));
            }
        } catch (SQLException throwables) {
            log.error(throwables);
            throw new DataNotFoundException();
        }
        return users;
    }

    @Override
    public Set<Integer> addActivitiesToUser(User user) throws DataNotFoundException {
        Set<Integer> activitySet = new TreeSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(Query.FIND_ACTIVITIES_BY_USER)) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activitySet.add(resultSet.getInt(1));
            }
            user.setActivitiesId(activitySet);
            return activitySet;
        } catch (SQLException throwables) {
            log.error(throwables);
            throw new DataNotFoundException();
        }
    }

    @Override
    public List<User> findAllUsersWithLimit(Integer limit, Integer offset, String order) throws DataNotFoundException {
        return findAllFromToWithOrderParam(connection, User.class, limit, offset, Query.FIND_ALL_USERS_ORDER, order);
    }


    @Override
    public boolean addUserToActivity(Activity activity, User user) throws DataNotFoundException {
        boolean result;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Query.ADD_USER_TO_ACTIVITY)) {
            preparedStatement.setInt(1, activity.getId());
            preparedStatement.setInt(2, user.getId());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            log.error(throwables);
            throw new DataNotFoundException();
        }
        return result;
    }

    @Override
    public boolean deleteUserFromActivity(Integer activityId, Integer userId) {
        return deleteFromDBWithTwoValue(connection, Query.DELETE_USER_FROM_ACTIVITY, activityId, userId);
    }

    public Integer checkIfUserAlreadyInThisActivity(String activityId, String userId) throws DataNotFoundException {
        return calculateRowCountsWithConditionAndWhereParam(connection, Query.CHECK_IF_USER_IN_ACTIVITY, activityId, userId);
    }

    @Override
    public Integer calculateUsersInActivity(String value) throws DataNotFoundException {
        return calculateRowCountsWithCondition(connection, Query.COUNT__ALL_CONNECTING_USERS_BY_ACTIVITY, value);
    }

    @Override
    public Integer calculateAllUsers() throws DataNotFoundException {
        return calculateRowCounts(connection, "user");
    }

    @Override
    public List<User> findUsersByRole(Role role) throws DataNotFoundException {
        return findAsListBy(connection, User.class, Query.FIND_USERS_BY_RolE, role.toString());
    }


    @Override
    public boolean createUser(User user) {
        return addToDB(connection, user, Query.CREATE_USER);

    }

    @Override
    public boolean updateUser(User user) {
        // 6 = resultset(id.nubmer)
        return updateInDB(connection, user, Query.UPDATE_USER, 6, user.getId());
    }


    @Override
    public boolean deleteUser(User user) {
        return deleteFromDB(connection, Query.DELETE_USER, user.getId());
    }


}
