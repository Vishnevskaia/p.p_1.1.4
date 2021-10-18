package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Util {
    public static void main(String[] args) {

    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "password";
    private static Connection connection;
    private static final String dialect = "org.hibernate.dialect.MySQL8Dialect";
    private static SessionFactory sessionFactory = null;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, DB_Driver);
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, user);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, dialect);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "none");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Connection getConnect() {

        try {
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Драйвер не найден!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL!");
        }
        return connection;
    }
}

