package com.example.javafx;

import entities.Request;
import entities.Timestamp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddRequest extends Application {

    @FXML
    public TextArea descriptionTextArea;
    public DatePicker dayDatePicker;
    public ComboBox chooseComboBox;
    @FXML
    public Spinner<Integer> hourSpinner, minuteSpinner;
    @FXML
    Button sendRequestButton, cancelButton;
    DatabaseService db= new DatabaseService();

    public AddRequest() throws SQLException {
    }

    @FXML
    public void initialize()
    {
        chooseComboBox.getItems().addAll("Stop", "Start");
        dayDatePicker.setValue(LocalDate.now());
        SpinnerValueFactory<Integer> valueFactoryHour= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);
        SpinnerValueFactory<Integer> valueFactoryMinute= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);

        valueFactoryHour.setValue(00);
        valueFactoryMinute.setValue(00);
        hourSpinner.setValueFactory(valueFactoryHour);
        minuteSpinner.setValueFactory(valueFactoryMinute);


    }

    @FXML
    private void sendRequestButtonOnAction(ActionEvent e) throws SQLException, IOException {
        boolean is_start= true;
        if(chooseComboBox.getSelectionModel().getSelectedItem() == "Stop")
        {
            is_start = false;
        }
        Timestamp timestamp = new Timestamp(Login.logInUser.getId(), java.sql.Date.valueOf(dayDatePicker.getValue()),
                java.sql.Time.valueOf(hourSpinner.getValue().toString()+":"
                        +minuteSpinner.getValue().toString()+":00"),is_start,
                descriptionTextArea.getText());
        db.createTimestamp(timestamp);
        //change Window
        Windows.changeWindow(sendRequestButton, "User.fxml");
        //Todo: actually it should send a request. Ask Leon.
    }
    @FXML
    private void cancelButtonOnAction(ActionEvent e) throws SQLException, IOException {
        Windows.changeWindow(cancelButton, "User.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
