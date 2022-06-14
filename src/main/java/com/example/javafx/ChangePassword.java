package com.example.javafx;

import entities.UserEntity;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;

public class ChangePassword extends Application {
    @FXML
    Button changePasswordButton, cancelButton;
    @FXML
    TextField previousPassword, newPassword, confirmNewPassword;
    @FXML
    Label errorLabel;
    @FXML
    AnchorPane pane;
    DatabaseService db = new DatabaseService();

    public ChangePassword() throws SQLException {
    }

    @FXML
    private void initialize()
    {
        errorLabel.setVisible(false);
    }
    @FXML
    private void changePasswordButtonOnAction(ActionEvent event) throws SQLException, IOException {
        UserEntity autheticateUser =db.validateData(Login.logInUserEntity.getEmail(), previousPassword.getText());
        if(autheticateUser == null)
        {
            errorLabel.setText("Enter previous password again!");

        }else if(!newPassword.getText().equals(confirmNewPassword.getText()))
        {
            errorLabel.setText("Passwords do not match!");
            errorLabel.setVisible(true);
        }else if(autheticateUser!= null)
        {
            errorLabel.setText("Password updated!");
            db.updatePassword(Login.logInUserEntity.getId(), newPassword.getText());
            autheticateUser=null;
            Windows.closePane(pane);
        }
    }
    @FXML
    private void cancelButtonOnAction(ActionEvent event) throws IOException {
        Windows.closePane(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
