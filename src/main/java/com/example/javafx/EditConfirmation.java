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
    public CheckBox deleteEntryCheckBox,changeEntryCheckBox;
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



        deleteEntryCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                changeEntryCheckBox.setSelected(false);
                deleteEntryCheckBox.setSelected(true);
                newTimeLabel.setVisible(false);
                descriptionLabel.setVisible(true);
                punktLabel.setVisible(false);
                hourComboBox.setVisible(false);
                minuteComboBox.setVisible(false);
                descriptionTextArea.setVisible(true);
            }
        });
        changeEntryCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {

                changeEntryCheckBox.setSelected(true);
                deleteEntryCheckBox.setSelected(false);
                newTimeLabel.setVisible(true);
                descriptionLabel.setVisible(true);
                punktLabel.setVisible(true);
                hourComboBox.setVisible(true);
                minuteComboBox.setVisible(true);
                descriptionTextArea.setVisible(true);
            }
        });
    }
    @FXML
    protected void changeEntryOnAction(ActionEvent e)
    {

        //Date date= new Date(EditRequest.timestamp.getDate().valueOf() );
       // Request request = new Request(EditRequest.timestamp.getId(), )
       // db.createRequest();
       // EditRequest.timestamp

    }
    @FXML
    protected void deleteEntryOnAction(ActionEvent e)
    {

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
