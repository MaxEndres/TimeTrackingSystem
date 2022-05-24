package com.example.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePassword extends Application {
    @FXML
    Button changePasswordButton, cancelButton;
    @FXML
    TextField previousPassword, newPassword, confirmNewPassword;
    @FXML
    Label errorLabel;

    @FXML
    private void initialize()
    {
        errorLabel.setVisible(false);
    }
    @FXML
    private void changePasswordButtonOnAction(ActionEvent event)
    {
        if(previousPassword.getText() != "")
        {
            errorLabel.setText("Enter previous password again!");

        }else if(newPassword.getText()!=confirmNewPassword.getText())
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
