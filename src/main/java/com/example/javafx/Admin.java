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
    Button createButton, searchUserButton, inboxButton;
    @FXML
    AnchorPane pane;

    @FXML
    protected void createButtonOnAction(ActionEvent event) throws IOException {
        Windows.loadWindow("CreateUser.fxml", pane);
    }
    @FXML
    protected void searchUserButtonOnAction(ActionEvent event) throws IOException {
        // windows.loadWindow("CreateUser.fxml", pane);
        //Windows.openWindow("User.fxml");
        Windows.loadWindow("SearchUser.fxml",pane);
    }
    @FXML
    protected void inboxButtonOnAction(ActionEvent event) throws IOException {
        Windows.openWindow("Requests.fxml");
    }
    @FXML
    protected void userViewButtonOnAction(ActionEvent event) throws IOException {
        Windows.openWindow("User.fxml");
    }
    @FXML
    protected void settingsButtonOnAction(ActionEvent event) throws IOException {
        Windows.openWindow("Settings.fxml");
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
