package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String dbTable = "users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(50) not NULL, " +
                " last_name VARCHAR (50) not NULL, " +
                " age INTEGER not NULL, " +
                " PRIMARY KEY (id));";
        Connection connection = Util.getConnection();
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint("Save");
            PreparedStatement psSt = connection.prepareStatement(SQL);
            psSt.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS users;";
        Connection connection = Util.getConnection();
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint("Save");
            PreparedStatement psSt = connection.prepareStatement(SQL);
            psSt.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "INSERT INTO " + dbTable + " (name, last_name, age) VALUES (?,?,?)";
        Connection connection = Util.getConnection();
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint("Save");
            PreparedStatement prST = connection.prepareStatement(insert);
            prST.setString(1, name);
            prST.setString(2, lastName);
            prST.setString(3, String.valueOf(age));
            prST.addBatch();
            prST.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE id = " + id;
        Connection connection = Util.getConnection();
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint("Save");
            PreparedStatement psSt = connection.prepareStatement(SQL);
            psSt.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String s = "SELECT * FROM " + dbTable;
        Connection connection = Util.getConnection();
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint("Save");
            PreparedStatement psSt = connection.prepareStatement(s);
            ResultSet resultSet = psSt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte) resultSet.getInt(4));
                usersList.add(user);
            }
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String SQL = "DELETE FROM users";
        Connection connection = Util.getConnection();
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint();
            PreparedStatement psSt = connection.prepareStatement(SQL);
            psSt.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }
}
