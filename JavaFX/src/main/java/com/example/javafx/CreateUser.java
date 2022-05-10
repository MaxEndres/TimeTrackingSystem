package com.example.javafx;

import Classes.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateUser extends Application {
    @FXML
    TextField firstName, lastName, email, id;
    @FXML
    ComboBox department;
    @FXML
    DatePicker startDay;
    @FXML
    Button confirmButton, cancelButton;

    @FXML
    public void initialize()
    {
        //startDay.setValue();
        //id
        //email
    }

    @FXML
    protected void confirmButtonOnAction(ActionEvent event)
    {
        User user = new User();
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setEmail(email.getText());
        user.setDepartment("");
        //user.setUserId("");
        //user.setPassword("");
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
