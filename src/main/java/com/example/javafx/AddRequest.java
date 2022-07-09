package com.example.javafx;

import entities.RequestEntity;
import entities.TimestampEntity;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AddRequest extends Application {

    @FXML
    public TextArea descriptionTextArea;
    public DatePicker dayDatePicker;
    public ComboBox chooseComboBox;
    @FXML
    public Spinner hourStartSpinner, minuteStartSpinner, hourStopSpinner, minuteStopSpinner;
    @FXML
    Button sendRequestButton, cancelButton;
    Alert a = new Alert(Alert.AlertType.ERROR);
    DatabaseService db= new DatabaseService();

    public AddRequest() throws SQLException {
    }

    @FXML
    public void initialize()
    {

        dayDatePicker.setValue(LocalDate.now());


        SpinnerValueFactory<Integer> valueFactoryHourStart= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);
        SpinnerValueFactory<Integer> valueFactoryMinuteStart= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);

        SpinnerValueFactory<Integer> valueFactoryHourStop= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);
        SpinnerValueFactory<Integer> valueFactoryMinuteStop= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);

        valueFactoryHourStart.setValue(Integer.valueOf(00));
        valueFactoryMinuteStart.setValue(Integer.valueOf(00));

        hourStartSpinner.setValueFactory(valueFactoryHourStart);
        minuteStartSpinner.setValueFactory(valueFactoryMinuteStart);

        valueFactoryHourStop.setValue(Integer.valueOf(00));
        valueFactoryMinuteStop.setValue(Integer.valueOf(00));

        hourStopSpinner.setValueFactory(valueFactoryHourStop);
        minuteStopSpinner.setValueFactory(valueFactoryMinuteStop);
    }

    @FXML
    private void sendRequestButtonOnAction(ActionEvent e) throws SQLException, IOException {
        String hourStart = hourStartSpinner.getValue().toString();
        String minuteStart = minuteStartSpinner.getValue().toString();
        String hourStop = hourStopSpinner.getValue().toString();
        String minuteStop = minuteStopSpinner.getValue().toString();
        Time start = java.sql.Time.valueOf(hourStart + ":" + minuteStart + ":00");
        Time stop = java.sql.Time.valueOf(hourStop + ":" + minuteStop + ":00");

        if(stop.before(start)){
            a.setContentText("Stopp time is before start time!");
            a.show();
        }
        else {
            Date date= Date.from(dayDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            TimestampEntity timestamp = new TimestampEntity(Login.logInUserEntity.getId(),
                    start, stop, sqlDate);
            RequestEntity requestEntity = new RequestEntity(
                    start,
                    stop,
                    descriptionTextArea.getText(), "PENDING", "ADD_NEW");

            System.out.println("sql Date: " + sqlDate);
            System.out.println("start: " + start);
            System.out.println("stop: " + stop);
            System.out.println("UserID: " + Login.logInUserEntity.getId());
            if(db.checkTimeStampCollission(Login.logInUserEntity.getId(), sqlDate, start, stop)) {

                db.createRequestForNonExistingTimestamp(requestEntity, timestamp);
            }
            else{
                a.setContentText("Timestamp collission! \nThere already exists a timestamp for this date with the same time/collission time!");
                a.show();
            }
            //db.createRequestForExistingTimestamp(requestEntity);
            Windows.closeWindow(sendRequestButton);
        }

        /*
        TimestampEntity timestamp = new TimestampEntity(Login.logInUserEntity.getId(), java.sql.Date.valueOf(dayDatePicker.getValue()),
                java.sql.Time.valueOf(hourSpinner.getValue().toString()+":"
                        +minuteSpinner.getValue().toString()+":00"),is_start,
                descriptionTextArea.getText());
        db.createTimestamp(timestamp);
        //change Window

        //Todo: actually it should send a request. Ask Leon.

         */
    }
    @FXML
    private void cancelButtonOnAction(ActionEvent e) throws SQLException, IOException {
       // Windows.changeWindow(cancelButton, "User.fxml");
        Windows.closeWindow(cancelButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
