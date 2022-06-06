package com.example.javafx;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import utility.Windows;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Admin extends Application {
    public MenuButton nameMenuButton;
    public MenuItem changePassword;
    public MenuItem logOut;
    @FXML
    Button createButton, searchUserButton, inboxButton;
    @FXML
    AnchorPane pane;
    @FXML
    public void initialize()
    {
        nameMenuButton.setText(Login.logInUser.getForename() +" "+ Login.logInUser.getSurname());
    }


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
    protected void changePasswordOnAction(ActionEvent event) throws IOException {
        Windows.loadWindow("ChangePassword.fxml", pane);
    }
    @FXML
    protected void logOutOnAction(ActionEvent event) throws IOException {
        //TODO: Confirmation
        Login.logInUser = null;
        Windows.closeWindow(searchUserButton);
        Windows.openWindow("hello-view.fxml");
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
