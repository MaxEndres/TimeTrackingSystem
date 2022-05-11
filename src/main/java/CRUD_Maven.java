import java.io.IOException;
import java.sql.*;

public class CRUD_Maven {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        String dbName = "OnPointDatabase";
        String userName = "indawewdh";
        String password = "kidCm30B";
        String hostname = "jdbc:mysql://ec2-3-68-76-92.eu-central-1.compute.amazonaws.com:3306/test_table";
        String port = "3306";

        //String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;

        //Class.forName("com.mysql.jdbc.driver");
        Connection conn = DriverManager.getConnection(hostname,userName,password);


        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO test_table.new_table (firstName, lastName) VALUES (?,?)");
        String firstName = "Harris ";
        String lastName = "Nuhanovic";
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.executeUpdate();
        


    }

}
