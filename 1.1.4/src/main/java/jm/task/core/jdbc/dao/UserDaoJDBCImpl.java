package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Statement;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public  class UserDaoJDBCImpl implements UserDao {



    public Connection connection;
    public Statement statement;

    public UserDaoJDBCImpl() {
        connection = Util.getConnect();

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createUsersTable() {
        Statement stm = this.statement;

        try {
            stm.executeUpdate("CREATE TABLE mydbtest.test1 (" +
                    "id bigint AUTO_INCREMENT primary key," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastName varchar(45) NOT NULL, " +
                    "age tinyint)");
            //System.out.println("Таблица создана!");
        } catch (SQLException e) {

        }

        return false;
    }

    public void dropUsersTable() {
        Statement stm = this.statement;

        try {
            String dropTable = "DROP table mydbtest.test1";
            stm.executeUpdate(dropTable);
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO mydbtest.test1 (id, name_, lastName_, age_) values (DEFAULT, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            int rows = ps.executeUpdate();

            System.out.println("USER " + name + " добавлен! " + rows + " строк добавлено!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        PreparedStatement ps1 = null;

        try {

            String delete = "DELETE FROM mydbtest.test1 where id = ?";
            ps1 = connection.prepareStatement(delete);
            ps1.setLong(1, id);

            int rows = ps1.executeUpdate();

           // System.out.println("USER " + id + " удален! " + rows + " строк удалено!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        Statement stm = this.statement;
        String getAll = "SELECT * FROM mydbtest.test1";
        List usersList = new LinkedList();
        try {
            ResultSet resSet = stm.executeQuery(getAll);

            while (resSet.next()) {
                Long i = resSet.getLong("id");
                String str1 = resSet.getString("name_");
                String str2 = resSet.getString("lastName_");
                Byte j = resSet.getByte("age_");

                User user = new User(i, str1, str2, j);
                usersList.add(user);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("ResultSet получен!");

        return usersList;
    }

    public void cleanUsersTable() {
        Statement stm = this.statement;

        try {

            String delete = "DELETE FROM mydbtest.test1";
            stm.executeUpdate(delete);
            //System.out.println("Все USERS удалены");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
