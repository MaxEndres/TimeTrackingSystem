package utility;

import entities.RequestEntity;
import entities.TimestampEntity;
import entities.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;


public class DatabaseService {

    final private String dbUserName = "indawewdh";
    final private String dbPassword = "kidCm30B";
    final private String dbHostname = "jdbc:mysql://ec2-3-68-76-92.eu-central-1.compute.amazonaws.com:3306/onpoint";
    private final Connection dbconn = DriverManager.getConnection(dbHostname, dbUserName, dbPassword);

    public DatabaseService() throws SQLException {
    }

    public ResultSet timeStampsForCSV(int userId) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT id,date,start,stop,CAST(TIMEDIFF(stop,start) AS CHAR) AS 'worked Time' FROM onpoint.timestamps WHERE timestamps.user_id = ?;");
        preparedStatement.setInt(1, userId);
        ResultSet queryOutput = preparedStatement.executeQuery();

        return queryOutput;
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
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT users.id,users.is_deleted, departments.name AS department, start_day, forename, surname, email, password, salt, target_hours, is_admin, is_first_login \n" +
                " FROM onpoint.users\n" +
                " JOIN onpoint.departments ON users.department_id = departments.id;");
        ObservableList<UserEntity> userEntityList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        int workedHours=0;
        while (queryOutput.next()) {
            if(queryOutput.getBoolean("is_deleted"))
            {
                continue;

            }else
            {
                UserEntity queryUser = new UserEntity(queryOutput.getInt("id"),
                        queryOutput.getString("department"),
                        queryOutput.getDate("start_day"),
                        queryOutput.getString("forename"),
                        queryOutput.getString("surname"),
                        queryOutput.getString("email"),
                        queryOutput.getString("password"),
                        queryOutput.getString("salt"),
                        queryOutput.getInt("target_hours"),
                        queryOutput.getBoolean("is_admin"),
                        queryOutput.getBoolean("is_first_login"));


                workedHours= getWorkedHours(queryUser, LocalDate.now().getMonthValue()); //TODO: fixMe
                queryUser.setWorkedHours(workedHours);
                userEntityList.add(queryUser);
            }

        }
        return userEntityList;
    }


    // list all Requests filtered by their status
    public ObservableList<RequestEntity> listAllRequests(String status) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT timestamp_id,  new_time_start, new_time_stop, description, status.name AS status, type.name AS type, timestamps.user_id AS user_id \n" +
                "FROM onpoint.requests\n" +
                "JOIN onpoint.status ON requests.status_id = status.id\n" +
                "JOIN onpoint.type ON requests.type_id = type.id\n" +
                "JOIN onpoint.timestamps ON requests.timestamp_id = timestamps.id " +
                "WHERE status.name = " + "'" + status+ "';");
        ObservableList<RequestEntity> requestEntityList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
            requestEntityList.add(new RequestEntity(queryOutput.getInt("timestamp_id"),
                    queryOutput.getTime("new_time_start"),
                    queryOutput.getTime("new_time_stop"),
                    queryOutput.getString("description"),
                    queryOutput.getString("status"),
                    queryOutput.getString("type"),
                    queryOutput.getInt("user_id")));
        }
        return requestEntityList;
    }

    // list all requests filtered by their userID
    public ObservableList<RequestEntity> listAllRequests(int userId) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT timestamp_id, requests.is_deleted, new_time_start, new_time_stop, description, status.name AS status, type.name AS type, timestamps.user_id AS user_id \n" +
                "FROM onpoint.requests\n" +
                "JOIN onpoint.status ON requests.status_id = status.id\n" +
                "JOIN onpoint.type ON requests.type_id = type.id\n" +
                "JOIN onpoint.timestamps ON requests.timestamp_id = timestamps.id " +
                "WHERE timestamps.user_id = "+userId+ ";");
        ObservableList<RequestEntity> requestEntityList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
            if(queryOutput.getBoolean("is_deleted"))
            {
                continue;
            }else
            {
                requestEntityList.add(new RequestEntity(queryOutput.getInt("timestamp_id"),
                        queryOutput.getTime("new_time_start"),
                        queryOutput.getTime("new_time_stop"),
                        queryOutput.getString("description"),
                        queryOutput.getString("status"),
                        queryOutput.getString("type"),
                        queryOutput.getInt("user_id")));
            }
        }
        return requestEntityList;
    }

    // list all requests filtered by their userID and status
    public ObservableList<RequestEntity> listAllRequests(int userId, String status) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT timestamp_id, requests.is_deleted, new_time_start, new_time_stop, description, status.name AS status, type.name AS type, timestamps.user_id AS user_id \n" +
                "FROM onpoint.requests\n" +
                "JOIN onpoint.status ON requests.status_id = status.id\n" +
                "JOIN onpoint.type ON requests.type_id = type.id\n" +
                "JOIN onpoint.timestamps ON requests.timestamp_id = timestamps.id " +
                "WHERE timestamps.user_id = "+userId+ " AND status.name ="+status+";");
        ObservableList<RequestEntity> requestEntityList = FXCollections.observableArrayList();
        ResultSet queryOutput = preparedStatement.executeQuery();
        while (queryOutput.next()) {
            if(queryOutput.getBoolean("is_deleted"))
            {
                continue;
            }else
            {
                requestEntityList.add(new RequestEntity(queryOutput.getInt("timestamp_id"),
                        queryOutput.getTime("new_time_start"),
                        queryOutput.getTime("new_time_stop"),
                        queryOutput.getString("description"),
                        queryOutput.getString("status"),
                        queryOutput.getString("type"),
                        queryOutput.getInt("user_id")));
            }
        }
        return requestEntityList;
    }

    public void updateTimestamp(Time stopTime) throws SQLException {
        PreparedStatement preparedStatement =
                dbconn.prepareStatement("" +
                        " UPDATE onpoint.timestamps SET stop = '" + stopTime+"' WHERE stop IS NULL; ");
        preparedStatement.executeUpdate();
        System.out.println("UPDATED");
    }

    // check if timestamp has already been requested
    public boolean checkRequestTable(int timestampId) throws SQLException {
        PreparedStatement preparedStatement =
                dbconn.prepareStatement("SELECT count(*) FROM onpoint.requests WHERE requests.timestamp_id =" + timestampId+"  ;");
        ResultSet queryOutput = preparedStatement.executeQuery();
        queryOutput.next();
        int count = queryOutput.getInt(1);
        if(count == 0)
        {
            return false;
        }else
        {
            return true;
        }
    }

    // Check if timestamp has stop
    public TimestampEntity checkTimestamp(int userId) throws SQLException
    {
        PreparedStatement preparedStatement =
                dbconn.prepareStatement("SELECT count(*) FROM onpoint.timestamps WHERE stop IS NULL AND user_id = "+ userId + " ;");
        ResultSet queryOutput = preparedStatement.executeQuery();
        queryOutput.next();
        int count = queryOutput.getInt(1);
        if(count == 0)
        {
            return null;
        } else {
            PreparedStatement preparedStatement2 =
                    dbconn.prepareStatement("SELECT * FROM onpoint.timestamps WHERE timestamps.stop IS NULL AND timestamps.user_id = " + userId + " ;");
            ResultSet queryOutput2 = preparedStatement2.executeQuery();
            queryOutput2.next();
            TimestampEntity timestamp = new TimestampEntity(queryOutput2.getInt("id"),
                    queryOutput2.getInt("user_id"),
                    queryOutput2.getTime("start"),
                    queryOutput2.getTime("stop"),
                    queryOutput2.getDate("date"));
            System.out.println("TIMESTAMPS: " + timestamp.getId());
            return timestamp;
        }
    }


    public void insertTimestamp(TimestampEntity timestamp) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.timestamps (user_id, start, date)\n" +
                "VALUES(?,?,?);");
        preparedStatement.setInt(1, timestamp.getUserId());
        preparedStatement.setTime(2, timestamp.getStart());
        preparedStatement.setDate(3, timestamp.getDate());
        preparedStatement.executeUpdate();
    }

    // list all Timestamps for User
    public ObservableList<TimestampEntity> listAllTimestamps(int userId) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT * FROM onpoint.timestamps WHERE timestamps.user_id = ?;");
        preparedStatement.setInt(1, userId);
        ResultSet queryOutput = preparedStatement.executeQuery();
        ObservableList<TimestampEntity> timestampList = FXCollections.observableArrayList();
        while (queryOutput.next()) {
            if(queryOutput.getBoolean("is_deleted"))
            {
                continue;
            }else
            {
                timestampList.add(new TimestampEntity(queryOutput.getInt("id"),
                        queryOutput.getInt("user_id"),
                        queryOutput.getTime("start"),
                        queryOutput.getTime("stop"),
                        queryOutput.getDate("date")));
            }

        }
        return timestampList;
    }

    // create a request when a user wants to change a timestamp they created
    public void createRequest(RequestEntity requestEntity) throws SQLException {
        if(checkRequestTable(requestEntity.getTimestampId()))
        {
            //denyRequest(requestEntity);
            PreparedStatement preparedStatement = dbconn.prepareStatement("DELETE FROM onpoint.requests WHERE timestamp_id = ?");
            preparedStatement.setInt(1, requestEntity.getTimestampId());
            preparedStatement.executeUpdate();
        }
        PreparedStatement preparedStatement = dbconn.prepareStatement("INSERT INTO onpoint.requests (timestamp_id, new_time_start, new_time_stop, description, status_id, type_id)" +
                "VALUES(?,?,?,?,(SELECT id FROM onpoint.status WHERE name = ?),(SELECT id FROM type WHERE name = ?));");
        preparedStatement.setInt(1, requestEntity.getTimestampId());
        preparedStatement.setTime(2, requestEntity.getNewTimeStart());
        preparedStatement.setTime(3, requestEntity.getNewTimeStop());
        preparedStatement.setString(4, requestEntity.getDescription());
        preparedStatement.setString(5, requestEntity.getStatus());
        preparedStatement.setString(6, requestEntity.getType());
        preparedStatement.executeUpdate();
    }

    // admin can accept a request which then updates the desired timestamp
    public void acceptRequest(RequestEntity requestEntity) throws SQLException {
        if (requestEntity.getType().equals("UPDATE")) {
            PreparedStatement preparedStatement = dbconn.prepareStatement("UPDATE onpoint.timestamps SET start = ?, stop = ? WHERE id = ?");
            preparedStatement.setTime(1, requestEntity.getNewTimeStart());
            preparedStatement.setTime(2, requestEntity.getNewTimeStop());
            preparedStatement.setInt(3, requestEntity.getTimestampId());
            preparedStatement.executeUpdate();
        }/* else if (requestEntity.getType().equals("ADD_NEW")) {
            insertTimestamp(timestamp);

        }*/ else if(requestEntity.getType().equals("DELETE")) {
            PreparedStatement preparedStatement = dbconn.prepareStatement("UPDATE timestamps SET is_deleted = true WHERE id = ?");
            preparedStatement.setInt(1, requestEntity.getTimestampId());
            preparedStatement.executeUpdate();
        }
        PreparedStatement preparedStatement = dbconn.prepareStatement("UPDATE onpoint.requests SET status_id = 2 WHERE timestamp_id = ?");
        preparedStatement.setInt(1, requestEntity.getTimestampId());
        preparedStatement.executeUpdate();
    }

    // admin can deny a request
    public void denyRequest(RequestEntity requestEntity) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("UPDATE onpoint.requests SET status_id = 3 set is_deleted = true WHERE timestamp_id = ?");
        preparedStatement.setInt(1, requestEntity.getTimestampId());
        preparedStatement.executeUpdate();
    }

    // user wants to see how much hours they worked in a certain month (month is a number between 1 and 12)
    public int getWorkedHours(UserEntity userEntity, int month) throws SQLException
    {
        int workedHours =0;
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT onpoint.timestamps.start, onpoint.timestamps.stop FROM onpoint.timestamps" +
                " WHERE user_id = ? AND MONTH(date)= ?;");
        preparedStatement.setInt(1, userEntity.getId());
        preparedStatement.setInt(2, month);
        ResultSet queryOutput = preparedStatement.executeQuery();
        Duration duration;
        long durationInHours = 0;

        while (queryOutput.next())
        {
            duration = Duration.between(queryOutput.getTime("start").toLocalTime(),
                    queryOutput.getTime("stop").toLocalTime() );
            durationInHours = durationInHours + duration.getSeconds()/3600;
            System.out.printf("DURATION IN HOURS: %s ",duration.getSeconds()/3600); //prints the difference in each timestamp

        }
        System.out.println( userEntity.getTargetHours()-durationInHours);
        int timeDiff =(int) (durationInHours-userEntity.getTargetHours());
        return timeDiff ;
    }

    public String nameOfUser(int userId) throws SQLException {
        PreparedStatement preparedStatement = dbconn.prepareStatement("SELECT forename, surname FROM onpoint.users WHERE id = "+userId+";");
        String name="";
        ResultSet queryOutput = preparedStatement.executeQuery();
        while(queryOutput.next())
        {
            name = queryOutput.getString("forename")+" "+queryOutput.getString("surname");
        }
        return name;
    }

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
                rsUser.getBoolean("is_admin"),
                rsUser.getBoolean("is_first_login"));
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

    public boolean checkEmail(String _email) throws SQLException {
        //Check if Record with given email exists
        PreparedStatement stUser = dbconn.prepareStatement("SELECT * " +
                "FROM onpoint.users " +
                "WHERE email=?");
        stUser.setString(1, _email);
        ResultSet rsUser = stUser.executeQuery();
        if (!rsUser.next()) {
            System.out.println("email does not exist");
            return true;
        }else {
            return false;
        }
    }

    public void updatePassword(int userId, String password) throws SQLException {
        String salt = BCrypt.gensalt();
        String passwordMitSalt = BCrypt.hashpw(password,salt);
        PreparedStatement stUser = dbconn.prepareStatement("UPDATE onpoint.users " +
                "SET " +
                "password = '" + passwordMitSalt +
                "' ,salt = '"+ salt + "' WHERE id = " + userId );

        stUser.executeUpdate();

    }

    public void updateFirstLogIn(int userId) throws SQLException {
        PreparedStatement stUser = dbconn.prepareStatement("UPDATE onpoint.users "+
                " SET is_first_login = false WHERE id = "+ userId);
        stUser.executeUpdate();

    }

    public void updateUser(UserEntity userEntity) throws SQLException {
    // ToDo: only Update forename, surname and mail
        PreparedStatement preparedStatement =
                dbconn.prepareStatement(
                        " UPDATE onpoint.users SET onpoint.users.department_id = (SELECT departments.id FROM onpoint.departments WHERE onpoint.departments.name = ' "+ userEntity.getDepartment() +"'" +
                                "), onpoint.users.start_day = '"+userEntity.getStartDay()+ "' , onpoint.users.forename = '" + userEntity.getForename()+
                                "', onpoint.users.surname = '" +userEntity.getSurname()+"' , onpoint.users.email= ' " + userEntity.getEmail() +
                                "', onpoint.users.target_hours = "+ userEntity.getTargetHours()+ " , onpoint.users.is_admin = " +userEntity.getIsAdmin()+
                                " WHERE onpoint.users.id = "+userEntity.getId() + ";");
        /*
        preparedStatement.setString(1, userEntity.getDepartment());
        preparedStatement.setDate(2, userEntity.getStartDay());
        preparedStatement.setString(3, userEntity.getForename());
        preparedStatement.setString(4, userEntity.getSurname());
        preparedStatement.setString(5, userEntity.getEmail());
        preparedStatement.setInt(6, userEntity.getTargetHours());
        preparedStatement.setBoolean(7, userEntity.getIsAdmin());
        preparedStatement.setInt(8, userEntity.getId());

         */
        preparedStatement.executeUpdate();
        System.out.println("*****");
    }
    public void updateUser(String department, Date startDay, String forename, String surname, String email, int targetHours, boolean isAdmin, int id) throws SQLException {

        PreparedStatement preparedStatement =
                dbconn.prepareStatement(
                        " UPDATE onpoint.users SET department_id = (SELECT id FROM departments WHERE name = ?), start_day = ?, forename = ?" +
                                ", surname = ?, email= ?, target_hours = ?, is_admin = ? WHERE id = ?;");
        preparedStatement.setString(1, department);
        preparedStatement.setDate(2, startDay);
        preparedStatement.setString(3, forename);
        preparedStatement.setString(4, surname);
        preparedStatement.setString(5, email);
        preparedStatement.setInt(6, targetHours);
        preparedStatement.setBoolean(7, isAdmin);
        preparedStatement.setInt(8, id);
        preparedStatement.executeUpdate();
        System.out.println("*****");
    }

    public void deleteUser(int id) throws SQLException {
        PreparedStatement preparedStatement =
                dbconn.prepareStatement(
                        " UPDATE onpoint.users SET is_deleted = 1 WHERE users.id = ?;");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }



}
