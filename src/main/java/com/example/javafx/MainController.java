package com.example.javafx;

import utility.Windows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainController {
    @FXML
    private Button loginButton, userButton;

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