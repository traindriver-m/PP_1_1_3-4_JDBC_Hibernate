package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ( " +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(100), " +
                    "age INT);";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS users;";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = String.format("INSERT INTO users (name, lastName, age) " +
                    "VALUES ('%s', '%s', %d);", name, lastName, age);
            statement.execute(sql);
            System.out.println("User с именем - " + name + " добавлен в базу данных.");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = String.format("DELETE FROM users WHERE id = %d", id);
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users ")) {
            connection.setAutoCommit(false);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(resultSet.getString("name"),
                            resultSet.getString("lastName"),
                            (byte) resultSet.getInt("age"));
                    user.setId(resultSet.getLong("id"));
                    users.add(user);
                }
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM users;";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
