package com.example.EpamFinalProject.dao.imp;

import com.example.EpamFinalProject.DBManager;
import com.example.EpamFinalProject.constants.Fields;
import com.example.EpamFinalProject.dao.UserDao;
import com.example.EpamFinalProject.entity.Activity;
import com.example.EpamFinalProject.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements UserDao {
    private static final String FIND_ALL_USERS = "SELECT * from user";
    private static final String CREATE_USER = "Insert into user(login,password,name,surname,role_id) values (?,?,?,?,?)";
    private static final String DELETE_USER = "DELETE FROM user where id = ?";
    private static final String UPDATE_USER = "UPDATE user set login = ?, password = ?, name = ?, surname =? where (id = ?)";

    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS)) {
            while (resultSet.next()) {
                mapUser(resultSet, users);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public void createUser(User user) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            // 1 - roleId = "client"
            preparedStatement.setInt(5, 1);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    public boolean updateUser(int toModifyUserId, User current) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, current.getLogin());
            preparedStatement.setString(2, current.getPassword());
            preparedStatement.setString(3, current.getName());
            preparedStatement.setString(4, current.getSurname());
            //
            preparedStatement.setInt(5, toModifyUserId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteUser(User user) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, user.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private void mapUser(ResultSet rs, List<User> users) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(Fields.USER_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setName(rs.getString(Fields.USER_NAME));
        user.setSurname(rs.getString(Fields.USER_SURNAME));
        user.setRoleId(rs.getInt(Fields.USER_ROLE));
        users.add(user);


    }
}
