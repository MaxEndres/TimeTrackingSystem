package com.example.javafx;

import entities.Request;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class EditConfirmation extends Application {

    @FXML
    public Label timestampLabel, currentEntryLabel,newTimeLabel, descriptionLabel, punktLabel;
    @FXML
    public TextArea descriptionTextArea;
    @FXML
    public ComboBox hourComboBox, minuteComboBox;
    @FXML
    public Button sendRequestButton;
    @FXML
    public RadioButton changeRadioButton, deleteRadioButton;
    DatabaseService db = new DatabaseService();

    public EditConfirmation() throws SQLException {
    }

    @FXML
    protected void initialize()
    {
        hourComboBox.getItems().add(getHours());
        minuteComboBox.getItems().add(getHours());
        timestampLabel.setText("Timestamp ID: " + EditRequest.timestamp.getId());
        currentEntryLabel.setText("Current Entry: " +EditRequest.timestamp.getDate()+ " " + EditRequest.timestamp.getTime());
        newTimeLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        punktLabel.setVisible(false);
        hourComboBox.setVisible(false);
        minuteComboBox.setVisible(false);
        descriptionTextArea.setVisible(false);


    }
    @FXML
    protected void addRequestOnAction(ActionEvent event)
    {
        if(changeRadioButton.isSelected())
        {
            newTimeLabel.setVisible(true);
            descriptionLabel.setVisible(true);
            punktLabel.setVisible(true);
            hourComboBox.setVisible(true);
            minuteComboBox.setVisible(true);
            descriptionTextArea.setVisible(true);
        }else if(deleteRadioButton.isSelected())
        {
            newTimeLabel.setVisible(false);
            descriptionLabel.setVisible(true);
            punktLabel.setVisible(false);
            hourComboBox.setVisible(false);
            minuteComboBox.setVisible(false);
            descriptionTextArea.setVisible(true);
        }
    }
    @FXML
    protected void sendRequestButtonOnAction(ActionEvent e) throws SQLException, IOException {

        if(changeRadioButton.isSelected()) {
            String hour = hourComboBox.getSelectionModel().getSelectedItem().toString();
            String minute = minuteComboBox.getSelectionModel().getSelectedItem().toString();

            Request request = new Request(EditRequest.timestamp.getId(), java.sql.Time.valueOf(hour + ":" + minute + ":00"),
                    descriptionTextArea.getText());
            db.createRequest(request);

        }else if(deleteRadioButton.isSelected())
        {
            //as Request table does not have either is delete or change timestamps,
            // so I added as automatically description
            //System.out.println("HOUR: " + EditRequest.timestamp.getTime());
            Request request = new Request(EditRequest.timestamp.getId(),
                    EditRequest.timestamp.getTime(),"Delete Timestamp\n"+ descriptionTextArea.getText() );
            db.createRequest(request);
        }
        Windows.changeWindow(sendRequestButton,"User.fxml");

    }
    @FXML
    protected void deleteEntryOnAction(ActionEvent e) throws SQLException {

    }



    public static void main(String[] args) {
        launch(args);
    }

    public static ObservableList<Integer> getHours()
    {
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for(int i=00; i<61; i++ )
        {
            hours.add(i);
        }

        return hours;
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
