package com.example.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import utility.DatabaseService;
import utility.Hashing;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;

public class FirstLogIn extends Application {


    public PasswordField newPassword;
    public PasswordField confirmNewPassword;
    public Button changePasswordButton;
    public Button cancelButton;
    public Label errorLabel;
    DatabaseService db = new DatabaseService();

    public FirstLogIn() throws SQLException {
    }
    @FXML
    public void initialize()
    {
        errorLabel.setVisible(false);
    }

    @FXML
    public void changePasswordButtonOnAction(ActionEvent e) throws SQLException, IOException {
        if(newPassword.getText().isBlank() || confirmNewPassword.getText().isBlank())
        {
            errorLabel.setVisible(true);
        }else if(newPassword.getText().equals(confirmNewPassword.getText()))
        {
            db.updatePassword(Login.logInUserEntity.getId(),newPassword.getText());
            db.updateFirstLogIn(Login.logInUserEntity.getId());
            //change to Login. TODO: Show confirmation windows
            Login.logInUserEntity =null;
            Windows.changeWindow(changePasswordButton, "hello-view.fxml");
        }else
        {
            errorLabel.setVisible(true);
        }



    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
