package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}




/*
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
 */