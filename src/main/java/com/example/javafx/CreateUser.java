package com.example.javafx;

import entities.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateUser extends Application {
    @FXML
    TextField forename, surname, email, id;
    @FXML
    ComboBox department, targetHours;
    @FXML
    DatePicker startDay;
    @FXML
    Button addUserButton, cancelButton;
    @FXML
    Label errorLabel;
    LocalDate todaysDate = LocalDate.now();

    @FXML
    public void initialize()
    {

        department.setItems(getDepartments());
        department.getSelectionModel().select(0);
        startDay.setValue(todaysDate);
        targetHours.setItems(getTargetHours());
        targetHours.getSelectionModel().select(5);
        errorLabel.setVisible(false);
        //startDay.setValue();
        //toDo: id sollte automatisch
        //toDo: email, domain von der Firma?
    }

    @FXML
    protected void addUserButtonOnAction(ActionEvent event) throws IOException {
        if(forename.getText().isBlank() ||surname.getText().isBlank()|| email.getText().isBlank() )
        {
            errorLabel.setVisible(true);

        }else
        {
            //Fragen nach Redundancies im Datenbank
            //add User
           // DatabaseService.createUser(email.getText(),forename.getText(), surname.getText(),
             //       email.getText(),"" ,targetHours.getSelectionModel(),0 );
            //meanwhile
            //Windows.changeWindow("");
        }
    }

    public static ObservableList<String> getDepartments()
    {
        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.addAll("Marketing", "Operations", "Finance", "Sales", "Human Resources", "Purchase");
        return departments;
    }
    public static ObservableList<Integer> getTargetHours()
    {
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        hours.addAll(1,2,3,4,5,6,7,8,9,10);
        return hours;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
