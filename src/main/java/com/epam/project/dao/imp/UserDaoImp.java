package com.epam.project.dao.imp;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UserDaoImp extends GenericAbstractDao<User> implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImp.class);
    private static final String FIND_ALL_USERS = "SELECT * from user";

    private static final String FIND_USERS_BY_RolE = "SELECT * from user where role = ?";
    private static final String CREATE_USER = "Insert into user(email,password,name,surname,role) values (?,?,?,?,?)";
    private static final String DELETE_USER = "DELETE FROM user where id = ?";
    private static final String UPDATE_USER = "UPDATE user set email = ?, password = ?, name = ?, surname =?, role =? " +
            "where (id = ?)";
    private static final String FIND_USER_BY_EMAIL = "SELECT * from user where email = ?;";
    private static final String FIND_USER_BY_ID = "SELECT * from user where user.id = ?;";
    private static final String FIND_ACTIVITIES_BY_USER = "Select id from activity join user_activity on user_activity.activity_id = activity.id where user_activity.user_id = ?;";


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
        return findAll(connection, User.class, FIND_ALL_USERS);
    }

    @Override
    public User findUserById(Integer id) throws DataNotFoundException {
        return findBy(connection, User.class, FIND_USER_BY_ID, id);
    }

    @Override
    public User findUserByLogin(String login) throws DataNotFoundException {
        return findBy(connection, User.class, FIND_USER_BY_EMAIL, login);

    }

    @Override
    public Set<Integer> addActivitiesToUser(User user) throws DataNotFoundException {
        Set<Integer> activitySet = new TreeSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACTIVITIES_BY_USER)) {
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
    public List<User> findUsersByRole(Role role) throws DataNotFoundException {
        return findAsListBy(connection, User.class, FIND_USERS_BY_RolE, role.toString());
    }

    @Override
    public boolean createUser(User user) {
        return addToDB(connection, user, CREATE_USER);

    }

    @Override
    public boolean updateUser(User user) {
        // 6 = resultset(id.nubmer)
        return updateInDB(connection, user, UPDATE_USER, 6, user.getId());
    }


    @Override
    public boolean deleteUser(User user) {
        return deleteFromDB(connection, DELETE_USER, user.getId());
    }


}
