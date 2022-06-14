package com.example.javafx;

import entities.UserEntity;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import utility.DatabaseService;
import utility.Windows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

public class Login {
    public static UserEntity logInUserEntity;
    public TextField usernameTextfield;
    public PasswordField passwordPasswordField;
    @FXML
    private Button loginButton, userButton;
    @FXML
    private ImageView logo;
    @FXML
    Label errorLabel;
    DatabaseService db = new DatabaseService();

    public Login() throws SQLException {
    }

    @FXML
    private void initialize()
    {
        logo.setVisible(true);
        errorLabel.setVisible(false);
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) throws IOException, SQLException {
        //Erstmal Admin Oberfläche
        //Windows.changeWindow(loginButton, "Admin.fxml");

        logInUserEntity = db.validateData(usernameTextfield.getText(), passwordPasswordField.getText());

        if(logInUserEntity == null)
        {
            errorLabel.setVisible(true);
        }else
        {
            //if first login then change password first
            if(logInUserEntity.getIsFirstLogin())
            {
                Windows.changeWindow(loginButton, "FirstLogIn.fxml");
            }else
            {
                //if Admin
                if(logInUserEntity.getIsAdmin())
                {
                    //adminadmin@onpoint.de
                    // wMumJ7hD
                    Windows.changeWindow(loginButton, "Admin.fxml");
                }else
                {
                    //User: harrish@onpoint.de
                    // Password: $!H9PLqT
                    Windows.changeWindow(loginButton, "User.fxml");
                }
            }



        }

    }
    @FXML
    protected void userButtonOnAction(ActionEvent event) throws IOException {
        Windows.changeWindow(userButton, "");
    }
}