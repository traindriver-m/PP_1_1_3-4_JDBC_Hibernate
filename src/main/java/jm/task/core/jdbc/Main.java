package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserDao dao = new UserDaoJDBCImpl();
        dao.createUsersTable();
        dao.saveUser("Nikolay", "Petrov", (byte) 26);
        dao.saveUser("Vasiliy", "Novikov", (byte) 44);
        dao.saveUser("Andrey", "Lomsadze", (byte) 35);
        dao.saveUser("Anna", "Clinton", (byte) 19);
        dao.getAllUsers().forEach(System.out::println);
        dao.cleanUsersTable();
        dao.dropUsersTable();
    }
}
