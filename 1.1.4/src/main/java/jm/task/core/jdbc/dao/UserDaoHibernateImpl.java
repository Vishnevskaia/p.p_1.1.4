package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

import org.hibernate.*;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public boolean createUsersTable() {
        try {
            Util util = new Util();
            Session session = util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User (" +
                    "id bigint PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastName VARCHAR(45) NOT NULL, " +
                    "age tinyint NOT NULL)";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Таблица User создана");
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public void dropUsersTable() {
        try {
            Util util = new Util();
            Session session = util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS mydbtest.User");
            query.executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Таблица удалена!");
        } catch (Exception e) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Util util = new Util();
            Session session = util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("user " + name + " добавлен!");
            session.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void removeUserById(long id) {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        if (user == null) {
            System.out.println("user id " + id + " не найден!");
        } else {
            session.delete(user);
            System.out.println("user id " + id + " удален!");
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        List<User> users = session.createQuery("FROM User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Util util = new Util();
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE User");
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
