package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {

        UserDaoHibernateImpl user1 = new UserDaoHibernateImpl();

        user1.createUsersTable();
        user1.saveUser("Anastasiia", "Vishnya", (byte) 57);
        user1.saveUser("Alex", "Alexievich", (byte) 25);
        user1.saveUser("Irma", "Vasiiileva", (byte) 75);
        user1.saveUser("Fedor", "Romanov", (byte) 15);
        user1.removeUserById(3);
        System.out.println(user1.getAllUsers());
        user1.cleanUsersTable();
        user1.dropUsersTable();

    }
}
