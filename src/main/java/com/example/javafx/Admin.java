package com.example.javafx;

import utility.Windows;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Admin extends Application {
    @FXML
    Button createButton, searchUser;
    @FXML
    AnchorPane pane;

    @FXML
    protected void createButtonOnAction(ActionEvent event) throws IOException {
       // windows.loadWindow("CreateUser.fxml", pane);
        Windows.loadWindow("CreateUser.fxml", pane);
    }
    @FXML
    protected void searchUserButtonOnAction(ActionEvent event) throws IOException {
        // windows.loadWindow("CreateUser.fxml", pane);
        Windows.openWindow("User.fxml");
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
