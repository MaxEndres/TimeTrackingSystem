package utility;

import entities.Request;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DatabaseService {

    final private String dbUserName = "indawewdh";
    final private String dbPassword = "kidCm30B";
    final private String dbHostname = "jdbc:mysql://ec2-3-68-76-92.eu-central-1.compute.amazonaws.com:3306/onpoint";
    private Connection dbconn = DriverManager.getConnection(dbHostname, dbUserName, dbPassword);

    public DatabaseService() throws SQLException {
    }

    // creating a new user
    public void createUser(User user) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO users (department_id, start_day, forename, surname, email, password, target_hours, is_admin)\n" +
                "VALUES((SELECT id FROM departments WHERE name = ?),?,?,?,?,?,?,?);");
        preparedStatement.setString(1, user.getDepartment());
        preparedStatement.setDate(2, user.getStartDay());
        preparedStatement.setString(3, user.getForename());
        preparedStatement.setString(4, user.getSurname());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.setInt(7, user.getTargetHours());
        preparedStatement.setBoolean(8, user.getIsAdmin());
        preparedStatement.executeUpdate();
    }

    // getting one specific user
    public User readUser(int id) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM users WHERE id = ?");
        preparedStatement.setInt(1, id);
        return (User) preparedStatement.executeQuery(); // probably does not work tho
    }

    // updating a user
    // TODO: what attributes have to be updated in which use cases?
    public void updateUser(int id) {

    }

    // deleting a user
    public void deleteUser(int id) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("DELETE FROM users WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    // creating a request
    public void createRequest(Request request) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.requests (timestamp_id, new_time) VALUES (?,?)");
        preparedStatement.setInt(1, request.getTimestampId());
        preparedStatement.setTime(2, request.getNewTime());
        preparedStatement.executeUpdate();
    }

    // getting a request
    public void readRequest(int timestampId) {

    }
    //
    public ObservableList<User> listAllUsers() throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM onpoint.users");
        ObservableList<User> userList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();

        while(queryOutput.next())
        {
            int id = queryOutput.getInt("id");
            String forename = queryOutput.getString("forename");
            String surname = queryOutput.getString("surname");
            String email = queryOutput.getString("email");
            int targetHours = queryOutput.getInt("target_hours");
            boolean isAdmin = queryOutput.getBoolean("is_admin");
            // TODO: String department = queryOutput.getString("department");
            // TODO userList.add(new User());
        }
        return userList;
    }

}
