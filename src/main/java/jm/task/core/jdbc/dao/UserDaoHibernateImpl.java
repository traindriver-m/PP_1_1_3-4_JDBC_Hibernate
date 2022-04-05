package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = new Util().getSessionFactory();
    private Session session = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(50), " +
                "lastName VARCHAR(100), age INT);").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User userReadFromDb = session.get(User.class, id);
        session.remove(userReadFromDb);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> usersList = session.createQuery("SELECT i FROM User i", User.class)
                .getResultList();
        session.getTransaction().commit();
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DELETE FROM users;").executeUpdate();
        session.getTransaction().commit();
    }
}
