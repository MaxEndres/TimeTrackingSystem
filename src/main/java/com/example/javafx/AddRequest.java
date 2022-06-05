package com.example.javafx;

import entities.Request;
import entities.Timestamp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utility.DatabaseService;

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
    Button sendRequestButton;
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
    private void sendRequestButtonOnAction(ActionEvent e) throws SQLException {
        boolean is_start= true;
        if(chooseComboBox.getSelectionModel().getSelectedItem() == "Stop")
        {
            is_start = false;
        }
        //Todo: change to user.getID()
        Timestamp timestamp = new Timestamp(1, java.sql.Date.valueOf(dayDatePicker.getValue()),
                java.sql.Time.valueOf(hourSpinner.getValue().toString()+":"
                        +minuteSpinner.getValue().toString()+":00"),is_start,
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
