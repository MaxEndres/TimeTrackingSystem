package entities;

import java.sql.*;

/**
 * <h1>Connect Class</h1>
 * This class contains all methods required to communicate with the MySQL Database, using the JDBC driver.
 *
 */

public class Connect {
    final static String userName = "indawewdh";
    final static String DBpassword = "kidCm30B";
    final static String hostname = "jdbc:mysql://ec2-3-68-76-92.eu-central-1.compute.amazonaws.com:3306/";



    public static void main(String[] args) throws SQLException {

        Connection conn = DriverManager.getConnection(hostname,userName,DBpassword);

        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO onpoint.users (foreName, surName, department_id, start_day, email, password, salt, target_hours, is_admin) VALUES (?,?,?,?,?,?,?,?,?)");
        String foreName = "Harris";
        String surName = "Nuhanovic";
        int department_id = 1;
        Date start_day = Date.valueOf("1970-01-01");
        String email = "Nuhanovic_harris@web.de";
        String password = "Nuhanovic";
        String salt = "Nuhanovic";
        int target_hours = 8;
        boolean is_admin = true;

        preparedStatement.setString(1, foreName);
        preparedStatement.setString(2, surName);
        preparedStatement.setInt(3, department_id);
        preparedStatement.setDate(4, start_day);
        preparedStatement.setString(5, email);
        preparedStatement.setString(6, password);
        preparedStatement.setString(7, salt);
        preparedStatement.setInt(8, target_hours);
        preparedStatement.setBoolean(9, is_admin);

        preparedStatement.executeUpdate();

    }

    public static void createUser(String _foreName, String _surName, int _department_id, Date _start_day, String _email, int _target_hours, boolean _is_admin) throws SQLException {

        String foreName = _foreName;
        String surName = _surName;
        int department_id = _department_id;
        Date start_day = _start_day;
        String email = _email;
        String password = Hashing.initPW(email);
        int target_hours = _target_hours;
        boolean is_admin = _is_admin;


        String sql_Insert = "INSERT INTO onpoint.users (foreName, surName, department_id, start_day, email, password, salt, target_hours, is_admin) VALUES (?,?,?,?,?,?,?,?,?)";

        Connection conn = DriverManager.getConnection(hostname,userName,DBpassword);


    }


}
