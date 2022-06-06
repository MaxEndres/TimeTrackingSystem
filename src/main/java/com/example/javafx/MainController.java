package com.example.javafx;

import entities.User;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.DatabaseService;
import utility.Windows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    public TextField usernameTextfield;
    public PasswordField passwordPasswordField;
    @FXML
    private Button loginButton, userButton;
    @FXML
    private ImageView logo;
    @FXML
    Label errorLabel;
    DatabaseService db = new DatabaseService();

    public MainController() throws SQLException {
    }

    @FXML
    private void initialize()
    {
        logo.setVisible(true);
        errorLabel.setVisible(false);
    }

    @FXML
    protected void loginButtonOnAction(ActionEvent event) throws IOException, SQLException {
        //Erstmal Admin Oberfläche
        //Windows.changeWindow(loginButton, "Admin.fxml");

        User logInUser= db.validateData(usernameTextfield.getText(), passwordPasswordField.getText());

        if(logInUser == null)
        {
            errorLabel.setVisible(true);
        }else
        {
            //if Admin
            if(logInUser.getIsAdmin())
            {
                Windows.changeWindow(loginButton, "Admin.fxml");
            }else
            {
                Windows.changeWindow(loginButton, "User.fxml");
            }
        }

    }
    @FXML
    protected void userButtonOnAction(ActionEvent event) throws IOException {
        Windows.changeWindow(userButton, "");
    }
}