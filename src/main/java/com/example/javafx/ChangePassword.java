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
import utility.Hashing;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;

public class ChangePassword extends Application {
    @FXML
    Button changePasswordButton, cancelButton;
    @FXML
    TextField previousPassword, newPassword, confirmNewPassword;
    @FXML
    Label errorLabel, label1, label2, label3;
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
            errorLabel.setVisible(true);
            errorLabel.setText("Enter previous password again!");

        }else if(!newPassword.getText().equals(confirmNewPassword.getText()))
        {
            errorLabel.setVisible(true);
            errorLabel.setText("Passwords do not match!");

        }else if(Hashing.pwInvalid(confirmNewPassword.getText())){
            errorLabel.setVisible(true);
            errorLabel.setText("Password has to match following criterea: " +
                    "\nA digit must occur at least once" +
                    "\nA lower case letter must occur at least once" +
                    "\nAn upper cast letter must occur at least once" +
                    "\nIts length is between 8-20 characters");
        }
        else if(autheticateUser!= null)
        {
            label1.setVisible(false);
            label2.setVisible(false);
            label3.setVisible(false);
            previousPassword.setVisible(false);
            newPassword.setVisible(false);
            confirmNewPassword.setVisible(false);
            errorLabel.setText("Password succesfully updated!");
            errorLabel.setVisible(true);
            changePasswordButton.setVisible(false);
            cancelButton.setText("Go Back");
            db.updatePassword(Login.logInUserEntity.getId(), newPassword.getText());
            autheticateUser=null;

           // Windows.closePane(pane);
        }
    }
    @FXML
    private void cancelButtonOnAction(ActionEvent event) throws IOException {
        Windows.closeWindow(cancelButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
