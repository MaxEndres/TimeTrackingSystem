package com.example.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.Windows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainController {
    @FXML
    private Button loginButton, userButton;
    @FXML
    private ImageView logo;

    @FXML
    private void initialize()
    {
        logo.setVisible(true);
    }

    @FXML
    protected void loginButtonOnAction(ActionEvent event) throws IOException {
        //Erstmal Admin Oberfläche
        Windows.changeWindow(loginButton, "Admin.fxml");

    }
    @FXML
    protected void userButtonOnAction(ActionEvent event) throws IOException {
        Windows.changeWindow(userButton, "");
    }
}