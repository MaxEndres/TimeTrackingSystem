package com.example.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utility.Windows;

import java.io.IOException;

public class EditRequest extends Application {

    @FXML
    Button changeButton;
@FXML
    protected void changeButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(changeButton, "EditConfirmation.fxml");
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
