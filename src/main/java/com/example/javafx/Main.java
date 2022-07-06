package com.example.javafx;

import entities.RequestEntity;
import entities.TimestampEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.DatabaseService;

import java.io.IOException;
import java.sql.*;

public class
Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws SQLException {
        DatabaseService db = new DatabaseService();
        launch();
    }
}

/*
        String dbUserName = "indawewdh";
        String dbPassword = "kidCm30B";
        String dbHostname = "jdbc:mysql://ec2-3-68-76-92.eu-central-1.compute.amazonaws.com:3306/onpoint";

        Connection conn = DriverManager.getConnection(dbHostname,dbUserName,dbPassword);

        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO onpoint.users (username, password) VALUES (?,?)");
        String username = "mockusername";
        String password = "mockpassword";
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
 */