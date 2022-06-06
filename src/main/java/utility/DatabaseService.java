package utility;

import entities.Request;
import entities.Timestamp;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Objects;


public class DatabaseService {

    final private String dbUserName = "indawewdh";
    final private String dbPassword = "kidCm30B";
    final private String dbHostname = "jdbc:mysql://ec2-3-68-76-92.eu-central-1.compute.amazonaws.com:3306/onpoint";
    private Connection dbconn = DriverManager.getConnection(dbHostname, dbUserName, dbPassword);

    public DatabaseService() throws SQLException {
    }


    // creating a new user
    public void createUser(User user) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO users (department_id, start_day, forename, surname, email, password, salt, target_hours, is_admin)\n" +
                "VALUES((SELECT id FROM departments WHERE name = ?),?,?,?,?,?,?,?,?);");
        preparedStatement.setString(1, user.getDepartment());
        preparedStatement.setDate(2, user.getStartDay());
        preparedStatement.setString(3, user.getForename());
        preparedStatement.setString(4, user.getSurname());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.setString(7, user.getSalt());
        preparedStatement.setInt(8, user.getTargetHours());
        preparedStatement.setBoolean(9, user.getIsAdmin());
        preparedStatement.executeUpdate();
    }

    // listing all available users
    public ObservableList<User> listAllUsers() throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT users.id, departments.name AS department, start_day, forename, surname, email, password, salt, target_hours, is_admin \n" +
                " FROM onpoint.users\n" +
                " JOIN onpoint.departments ON users.department_id = departments.id;");
        ObservableList<User> userList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
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

    // listing all requests
    public ObservableList<Request> listAllRequests() throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM requests ;");
        ObservableList<Request> requestList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
            requestList.add(new Request(queryOutput.getInt("timestamp_id"),
                    queryOutput.getTime("new_time"),
                    queryOutput.getString("description")));
        }
        return requestList;
    }
    //list all Timestamps from User

    public ObservableList<Timestamp> listAllTimestamps(int user_id) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM onpoint.timestamps WHERE user_id =" + user_id + ";");
        ObservableList<Timestamp> timestampList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
            timestampList.add(new Timestamp(queryOutput.getInt("id"),
                    queryOutput.getInt("user_id"),
                    queryOutput.getDate("date"),
                    queryOutput.getTime("time"),
                    queryOutput.getBoolean("is_start"),
                    queryOutput.getString("description")));
        }
        return timestampList;
    }

    // create a certain timestamp when a user presses start/pause/stop button
    public void createTimestamp(Timestamp timestamp) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.timestamps (user_id, date, time, is_start, description)\n" +
                "VALUES(?,?,?,?,?);");
        preparedStatement.setInt(1, timestamp.getUserId());
        preparedStatement.setDate(2, timestamp.getDate());
        preparedStatement.setTime(3, timestamp.getTime());
        preparedStatement.setBoolean(4, timestamp.getIsStart());
        preparedStatement.setString(5, timestamp.getDescription());
        preparedStatement.executeUpdate();
    }

    // create a request when a user wants to change a timestamp they created
    public void createRequest(Request request) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.requests (timestamp_id, new_time, description)" +
                "VALUES(?,?,?);");
        preparedStatement.setInt(1, request.getTimestampId());
        preparedStatement.setTime(2, request.getNewTime());
        preparedStatement.setString(3, request.getDescription());
        preparedStatement.executeUpdate();
    }

    // admin can accept a request which then updates the desired timestamp
    public void acceptRequest(Request request) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("UPDATE timestamps SET time = ? WHERE id = ?;");
        preparedStatement.setTime(1, request.getNewTime());
        preparedStatement.setInt(2, request.getTimestampId());
        preparedStatement.executeUpdate();
        // after accepting a request it will be deleted automatically by using the denyRequest() method
        denyRequest(request);
    }

    // admin can deny a request which simply deletes it from the database
    public void denyRequest(Request request) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("DELETE FROM requests WHERE timestamp_id = ?");
        preparedStatement.setInt(1, request.getTimestampId());
        preparedStatement.executeUpdate();
    }

    // user wants to see how much hours they worked in a certain month (month is a number between 1 and 12)
    public int getWorkedHours(User user, int month) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM timestamps WHERE MONTH(date) = ? AND user_id = ? ORDER BY time ASC;");
        preparedStatement.setInt(1, month);
        preparedStatement.setInt(2, user.getId());
        ResultSet queryOutput = preparedStatement.executeQuery();
        int workedHours = 0;
        Time tmpTime = null;
        while (queryOutput.next()) {
            if (queryOutput.getBoolean("is_start") == true) {
                tmpTime = queryOutput.getTime("time");
            } else {
                long timeDiff = queryOutput.getTime("time").getTime() - tmpTime.getTime(); // in milliseconds
                timeDiff = timeDiff / 1000 / 60 / 60; // in hours
                workedHours += timeDiff;
            }
        }
        return workedHours;
    }


    /**
     * <h1>validateData</h1>
     * <p>
     * The validateData function takes the input username and password from the GUI.
     * First it checks, if there is an existing account with the input credentials. If not, the return value is false.
     * After a matching email is found, the is_admin, salt and hashed password are fetched from the database.
     * The password input by the user via GUI is now hashed with the fetched salt using the Blowfish algorithm.
     * After that, the hashed user-password and the stored pw hash are compared. If the passwords are not matching,
     * the function returns false. If the passwords are matching, the function displays the GUI windows according to
     * the role of the user.
     *
     * @param _email
     * @param _password
     * @return
     */

    public void validateData(String _email, String _password) throws SQLException {

        String inputPassword = _password;

        String passwordHash = null;
        String salt = null;
        boolean is_admin = false;

        //Check if Record with given email exists
        PreparedStatement stEmailExists = dbconn.prepareStatement("SELECT email " +
                "FROM onpoint.users " +
                "WHERE email=?");

        stEmailExists.setString(1, _email);

        ResultSet rsEmailExists = stEmailExists.executeQuery();

        if (!rsEmailExists.next()) {

            System.out.println("email does not exist");
            return;

        }


        //prepare statments for validation
        PreparedStatement stHash = dbconn.prepareStatement("SELECT password " +
                "FROM onpoint.users " +
                "WHERE email=?");

        PreparedStatement stSalt = dbconn.prepareStatement("SELECT salt " +
                "FROM onpoint.users " +
                "WHERE email=?");

        PreparedStatement stIs_Admin = dbconn.prepareStatement("SELECT is_admin " +
                "FROM onpoint.users " +
                "WHERE email=?");

        stHash.setString(1, _email);
        stSalt.setString(1, _email);
        stIs_Admin.setString(1, _email);

        // execute queries and write into Resultsets
        ResultSet rsHash = stHash.executeQuery();
        ResultSet rsSalt = stSalt.executeQuery();
        ResultSet rsIs_Admin = stIs_Admin.executeQuery();


        //fetch records and write into variables
        if (rsHash.next()) {

            passwordHash = rsHash.getString("password");

        }

        if (rsSalt.next()) {

            salt = rsSalt.getString("salt");

        }

        if (rsIs_Admin.next()) {

            is_admin = rsIs_Admin.getBoolean("is_admin");

        }

        // hash user-input password with fetched salt
        inputPassword = BCrypt.hashpw(inputPassword, salt);


        // compare fetched passwordhash and user-input passwordhash
        if (!Objects.equals(passwordHash, inputPassword)) {

            System.out.println("false");
            return;

        }

            // check for role and display GUI
        if (is_admin) {

            //TODO: GUI ADMIN

        } else {

            //TODO: GUI USER

        }

        return;
    }
}
