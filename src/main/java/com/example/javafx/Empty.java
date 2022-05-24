package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Empty extends Application {
    @FXML
    Label emptyLabel;

    @FXML
    void initialize()
    {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
