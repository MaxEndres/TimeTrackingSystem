package com.example.javafx;

import entities.RequestEntity;
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

public class EditConfirmation extends Application {

    @FXML
    public Label timestampLabel, errorLabel, currentEntryLabel,newTimeLabel, descriptionLabel, punktLabel;
    @FXML
    public TextArea descriptionTextArea;
    public Spinner hourStartSpinner, minuteStartSpinner, hourStopSpinner, minuteStopSpinner;
    @FXML
    public Button sendRequestButton, cancelButton;
    @FXML
    public RadioButton changeRadioButton, deleteRadioButton;
    DatabaseService db = new DatabaseService();
    Alert a = new Alert(Alert.AlertType.ERROR);

    public EditConfirmation() throws SQLException {
    }

    @FXML
    protected void initialize()
    {
        errorLabel.setVisible(false);
        timestampLabel.setText("" + EditRequest.timestamp.getId());
        currentEntryLabel.setText(" Date " +EditRequest.timestamp.getDate());
        /*
        newTimeLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        punktLabel.setVisible(false);
        descriptionTextArea.setVisible(false);
        hourStartSpinner.setVisible(false);
        minuteStartSpinner.setVisible(false);

         */
        SpinnerValueFactory<Integer> valueFactoryHourStart= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);
        SpinnerValueFactory<Integer> valueFactoryMinuteStart= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);

        SpinnerValueFactory<Integer> valueFactoryHourStop= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);
        SpinnerValueFactory<Integer> valueFactoryMinuteStop= new SpinnerValueFactory.IntegerSpinnerValueFactory(00,60,00);

        Time timeStart = EditRequest.timestamp.getStart();
        String[] stringTimeStart = timeStart.toString().split(":");
        //START
        valueFactoryHourStart.setValue(Integer.valueOf(stringTimeStart[0]));
        valueFactoryMinuteStart.setValue(Integer.valueOf(stringTimeStart[1]));

        hourStartSpinner.setValueFactory(valueFactoryHourStart);
        minuteStartSpinner.setValueFactory(valueFactoryMinuteStart);

        //STOPP
        Time timeStop = EditRequest.timestamp.getStop();
        if(EditRequest.timestamp.getStop() == null)
        {
            valueFactoryHourStop.setValue(Integer.valueOf("18"));
            valueFactoryMinuteStop.setValue(Integer.valueOf("00"));
            hourStopSpinner.setValueFactory(valueFactoryHourStop);
            minuteStopSpinner.setValueFactory(valueFactoryMinuteStop);
            errorLabel.setVisible(true);
            cancelButton.setVisible(false);
        }else
        {
            String[] stringTimeStop = timeStop.toString().split(":");
            //STOP
            valueFactoryHourStop.setValue(Integer.valueOf(stringTimeStop[0]));
            valueFactoryMinuteStop.setValue(Integer.valueOf(stringTimeStop[1]));

            hourStopSpinner.setValueFactory(valueFactoryHourStop);
            minuteStopSpinner.setValueFactory(valueFactoryMinuteStop);
        }



    }
    @FXML
    protected void addRequestOnAction(ActionEvent event)
    {
        if(changeRadioButton.isSelected())
        {
            /*
            newTimeLabel.setVisible(true);
            descriptionLabel.setVisible(true);
            punktLabel.setVisible(true);
            hourStartSpinner.setVisible(true);
            minuteStartSpinner.setVisible(true);
            hourStopSpinner.setVisible(true);
            minuteStopSpinner.setVisible(true);
            descriptionTextArea.setVisible(true);
            *
             */

        }else if(deleteRadioButton.isSelected())
        {
            /*
            newTimeLabel.setVisible(false);
            descriptionLabel.setVisible(true);
            punktLabel.setVisible(false);
            hourStartSpinner.setVisible(false);
            minuteStartSpinner.setVisible(false);
            hourStopSpinner.setVisible(false);
            minuteStopSpinner.setVisible(false);
            descriptionTextArea.setVisible(true);

             */
        }
    }
    @FXML
    protected void sendRequestButtonOnAction(ActionEvent e) throws SQLException, IOException {
        String hourStart = hourStartSpinner.getValue().toString();
        String minuteStart = minuteStartSpinner.getValue().toString();
        String hourStop = hourStopSpinner.getValue().toString();
        String minuteStop = minuteStopSpinner.getValue().toString();
        Time start = java.sql.Time.valueOf(hourStart + ":" + minuteStart + ":00");
        Time stop = java.sql.Time.valueOf(hourStop + ":" + minuteStop + ":00");


        if(changeRadioButton.isSelected()) {
            if(stop.before(start)){
                a.setContentText("Stopp time is before start time!");
                a.show();
            }
            else {
                RequestEntity requestEntity = new RequestEntity(EditRequest.timestamp.getId(),
                        start,
                        stop,
                        descriptionTextArea.getText(), "PENDING", "UPDATE_START");
                db.createRequest(requestEntity);
                requestEntity = null;
                Windows.changeWindow(sendRequestButton,"User.fxml");
            }
        }else if(deleteRadioButton.isSelected())
        {
            if(stop.before(start)){
                a.setContentText("Stopp time is before start time!");
                a.show();

        }else {
                //as Request table does not have either is delete or change timestamps,
                // so I added as automatically description
                //TODO: Leon noch ein Field hinzufugen
                //System.out.println("HOUR: " + EditRequest.timestamp.getTime());
                RequestEntity requestEntity = new RequestEntity(EditRequest.timestamp.getId(),
                        java.sql.Time.valueOf(hourStart + ":" + minuteStart + ":00"),
                        java.sql.Time.valueOf(hourStop + ":" + minuteStop + ":00"),
                        descriptionTextArea.getText(), "PENDING", "DELETE");
                db.createRequest(requestEntity);
                requestEntity = null;
                Windows.changeWindow(sendRequestButton,"User.fxml");
            }
        }


    }
    @FXML
    protected void cancelButtonOnAction(ActionEvent e) throws SQLException, IOException {
        EditRequest.timestamp= null;
        Windows.closeWindow(cancelButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
