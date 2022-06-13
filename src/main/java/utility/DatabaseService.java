package utility;

import entities.RequestEntity;
import entities.TimestampEntity;
import entities.UserEntity;
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
    public void createUser(UserEntity userEntity) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.users (department_id, start_day, forename, surname, email, password, salt, target_hours, is_admin, is_first_login)\n" +
                "VALUES((SELECT id FROM departments WHERE name = ?),?,?,?,?,?,?,?,?,?);");
        preparedStatement.setString(1, userEntity.getDepartment());
        preparedStatement.setDate(2, userEntity.getStartDay());
        preparedStatement.setString(3, userEntity.getForename());
        preparedStatement.setString(4, userEntity.getSurname());
        preparedStatement.setString(5, userEntity.getEmail());
        preparedStatement.setString(6, userEntity.getPassword());
        preparedStatement.setString(7, userEntity.getSalt());
        preparedStatement.setInt(8, userEntity.getTargetHours());
        preparedStatement.setBoolean(9, userEntity.getIsAdmin());
        preparedStatement.setBoolean(10, userEntity.getIsFirstLogin());
        preparedStatement.executeUpdate();
    }

    // listing all available users
    public ObservableList<UserEntity> listAllUsers() throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT users.id, departments.name AS department, start_day, forename, surname, email, password, salt, target_hours, is_admin, is_first_login \n" +
                " FROM onpoint.users\n" +
                " JOIN onpoint.departments ON users.department_id = departments.id;");
        ObservableList<UserEntity> userEntityList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
            userEntityList.add(new UserEntity(queryOutput.getInt("id"),
                    queryOutput.getString("department"),
                    queryOutput.getDate("start_day"),
                    queryOutput.getString("forename"),
                    queryOutput.getString("surname"),
                    queryOutput.getString("email"),
                    queryOutput.getString("password"),
                    queryOutput.getString("salt"),
                    queryOutput.getInt("target_hours"),
                    queryOutput.getBoolean("is_admin"),
                    queryOutput.getBoolean("is_first_login")));
        }
        return userEntityList;
    }


    // listing all requests
    public ObservableList<RequestEntity> listAllRequests() throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT timestamp_id, new_time, description, status.name AS status, type.name AS type FROM onpoint.requests" +
                "JOIN onpoint.status ON requests.status_id = status.id" +
                "JOIN onpoint.type ON requests.type_id = type.id;");
        ObservableList<RequestEntity> requestEntityList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
            requestEntityList.add(new RequestEntity(queryOutput.getInt("timestamp_id"),
                    queryOutput.getTime("new_time"),
                    queryOutput.getString("description"),
                    queryOutput.getString("status"),
                    queryOutput.getString("type")));
        }
        return requestEntityList;
    }

    // list all Timestamps from User
    public ObservableList<TimestampEntity> listAllTimestamps(int userId) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM onpoint.timestamps WHERE user_id = ?;");
        preparedStatement.setInt(1, userId);
        ResultSet queryOutput = preparedStatement.executeQuery();
        ObservableList<TimestampEntity> timestampList = FXCollections.observableArrayList();
        while (queryOutput.next()) {
            timestampList.add(new TimestampEntity(queryOutput.getInt("id"),
                    queryOutput.getInt("user_id"),
                    queryOutput.getTimestamp("start"),
                    queryOutput.getTimestamp("stop")));
        }
        return timestampList;
    }

    // create a certain timestamp when a user presses start/pause/stop button
    public void createTimestamp(TimestampEntity timestamp) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.timestamps (user_id, start, stop)\n" +
                "VALUES(?,?,?);");
        preparedStatement.setInt(1, timestamp.getUserId());
        preparedStatement.setTimestamp(2, timestamp.getStart());
        preparedStatement.setTimestamp(3, timestamp.getStop());
        preparedStatement.executeUpdate();
    }

    // create a request when a user wants to change a timestamp they created
    public void createRequest(RequestEntity requestEntity) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.requests (timestamp_id, new_time, description, status_id, type_id)" +
                "VALUES(?,?,?,(SELECT id FROM status WHERE name = ?),(SELECT id FROM type WHERE name = ?));");
        preparedStatement.setInt(1, requestEntity.getTimestampId());
        preparedStatement.setTime(2, requestEntity.getNewTime());
        preparedStatement.setString(3, requestEntity.getDescription());
        preparedStatement.setString(4, requestEntity.getStatus());
        preparedStatement.setString(5, requestEntity.getType());
        preparedStatement.executeUpdate();
    }

    // admin can accept a request which then updates the desired timestamp
    public void acceptRequest(RequestEntity requestEntity) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("UPDATE timestamps SET time = ? WHERE id = ?;");
        preparedStatement.setTime(1, requestEntity.getNewTime());
        preparedStatement.setInt(2, requestEntity.getTimestampId());
        preparedStatement.executeUpdate();
        // after accepting a request it will be deleted automatically by using the denyRequest() method
        denyRequest(requestEntity);
    }

    // admin can deny a request which simply deletes it from the database
    public void denyRequest(RequestEntity requestEntity) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("DELETE FROM requests WHERE timestamp_id = ?");
        preparedStatement.setInt(1, requestEntity.getTimestampId());
        preparedStatement.executeUpdate();
    }

    // user wants to see how much hours they worked in a certain month (month is a number between 1 and 12)
    public int getWorkedHours(UserEntity userEntity, int month) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM timestamps WHERE MONTH(date) = ? AND user_id = ? ORDER BY time ASC;");
        preparedStatement.setInt(1, month);
        preparedStatement.setInt(2, userEntity.getId());
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

    // list All pending requests for a certain user


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
    public UserEntity validateData(String _email, String _password) throws SQLException {
        String inputPassword = _password;
        String passwordHash = null;
        String salt = null;
        //Check if Record with given email exists
        PreparedStatement stUser = dbconn.prepareStatement("SELECT * " +
                "FROM onpoint.users " +
                "WHERE email=?");
        stUser.setString(1, _email);
        ResultSet rsUser = stUser.executeQuery();
        if (!rsUser.next()) {
            System.out.println("email does not exist");
            return null;
        }
        UserEntity userEntity = new UserEntity(
                rsUser.getInt("id"),
                rsUser.getString("department_id"),
                rsUser.getDate("start_day"),
                rsUser.getString("forename"),
                rsUser.getString("surname"),
                rsUser.getString("email"),
                rsUser.getString("password"),
                rsUser.getString("salt"),
                rsUser.getInt("target_hours"),
                rsUser.getBoolean("is_admin"));
        passwordHash = userEntity.getPassword();
        salt = userEntity.getSalt();
        // hash user-input password with fetched salt
        inputPassword = BCrypt.hashpw(inputPassword, salt);
        // compare fetched passwordhash and user-input passwordhash
        if (!Objects.equals(passwordHash, inputPassword)) {
            System.out.println("false");
            return null;
        }
        return userEntity;
    }


}
