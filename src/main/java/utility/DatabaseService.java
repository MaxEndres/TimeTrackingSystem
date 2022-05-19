package utility;

import java.sql.*;

public class DatabaseService {

    final private String dbUserName = "indawewdh";
    final private String dbPassword = "kidCm30B";
    final private String dbHostname = "jdbc:mysql://ec2-3-68-76-92.eu-central-1.compute.amazonaws.com:3306/onpoint";
    private Connection dbconn = null;

    public DatabaseService() throws SQLException {
    }

    public void createUser(String username, String forename, String surname, String email, String password, int targetHours, boolean isAdmin ) throws SQLException {
        dbconn = DriverManager.getConnection(dbHostname,dbUserName,dbPassword);
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.users (username, forename, surname, email, password, target_hours, is_admin) VALUES (?,?,?,?,?,?,?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, forename);
        preparedStatement.setString(3, surname);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, password);
        preparedStatement.setInt(6, targetHours);
        preparedStatement.setBoolean(7, isAdmin);
        preparedStatement.executeUpdate();
    }



}
