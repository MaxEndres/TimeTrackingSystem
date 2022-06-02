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

    // listing all available users
    public ObservableList<User> listAllUsers() throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT id, departments.name AS department, start_day, forename, surname, email, password, salt, target_hours, is_admin\n" +
                "FROM users\n" +
                "JOIN departments ON users.department_id = departments.id;");
        ObservableList<User> userList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while(queryOutput.next())
        {
            userList.add(new User(queryOutput.getInt("id"),
                                    queryOutput.getString("department"),
                                    queryOutput.getDate("start_day"),
                                    queryOutput.getString("forename"),
                                    queryOutput.getString("surname"),
                                    queryOutput.getString("email"),
                                    queryOutput.getString("password"),
                                    queryOutput.getString("salt"),
                                    queryOutput.getInt("target_hours"),
                                    queryOutput.getBoolean("is_admin")));
        }
        return userList;
    }

}
