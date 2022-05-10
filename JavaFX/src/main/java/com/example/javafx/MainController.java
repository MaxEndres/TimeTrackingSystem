package com.example.javafx;

import helpFunctions.windows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {
    @FXML
    private Button loginButton, userButton;

    @FXML
    protected void loginButtonOnAction(ActionEvent event) throws IOException {
        //Erstmal Admin Oberfläche
        windows.changeWindow(loginButton, "Admin.fxml");

    }
    @FXML
    protected void userButtonOnAction(ActionEvent event) throws IOException {
        windows.changeWindow(userButton, "");
    }
}