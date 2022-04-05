package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private final String HOST = "jdbc:mysql://localhost:3306/users_db";
    private final String USERNAME = "root";
    private final String PASSWORD ="19770098";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String SHOWSQL = "true";
    private final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private final String CONTEXT = "thread";
    private Connection connection;

    public Util()  {
        try {
            connection = DriverManager.getConnection(HOST,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SessionFactory getSessionFactory(){
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", DRIVER);
        properties.put("hibernate.connection.url", HOST);
        properties.put("hibernate.connection.username",USERNAME);
        properties.put("hibernate.connection.password", PASSWORD);
        properties.put("hibernate.show_sql", SHOWSQL);
        properties.put("hibernate.dialect", DIALECT);
        properties.put("hibernate.current_session_context_class", CONTEXT);
        return new Configuration().setProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();
    }

    public Connection getConnection(){
        return connection;
    }
}
