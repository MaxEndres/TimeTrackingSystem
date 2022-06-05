package com.example.javafx;

import entities.Request;
import entities.Timestamp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utility.DatabaseService;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddRequest extends Application {

    public TextArea descriptionTextArea;
    public ComboBox hourComboBox;
    public ComboBox minuteComboBox;
    public DatePicker dayDatePicker;
    public ComboBox chooseComboBox;
    @FXML
    Button sendRequestButton;
    DatabaseService db= new DatabaseService();

    public AddRequest() throws SQLException {
    }

    @FXML
    public void initialize()
    {
        chooseComboBox.getItems().addAll("Stop", "Start");
        dayDatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void sendRequestButtonOnAction(ActionEvent e) throws SQLException {
        boolean is_start= true;
        if(chooseComboBox.getSelectionModel().getSelectedItem() == "Stop")
        {
            is_start = false;
        }
        //Todo: change to user.getID()
        Timestamp timestamp = new Timestamp(1, java.sql.Date.valueOf(dayDatePicker.getValue()),
                java.sql.Time.valueOf(hourComboBox.getSelectionModel().getSelectedItem().toString()+":"
                        +minuteComboBox.getSelectionModel().getSelectedItem().toString()+":00"),is_start,
                descriptionTextArea.getText());
        db.createTimestamp(timestamp);
        //Todo: actually it should send a request. Ask Leon.
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
