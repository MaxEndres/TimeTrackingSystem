package com.example.javafx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditConfirmation extends Application {

    @FXML
    public Label timeStampLabel, currentEntryLabel,newTimeLabel, descriptionLabel, punktLabel;
    @FXML
    public TextArea descriptionTextArea;
    @FXML
    public ChoiceBox hourChoiceBox, minuteChoiceBox;
    @FXML
    public Button sendRequestButton;
    @FXML
    public CheckBox deleteEntryCheckBox,changeEntryCheckBox;


    @FXML
    protected void initialize()
    {
        newTimeLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        punktLabel.setVisible(false);
        hourChoiceBox.setVisible(false);
        minuteChoiceBox.setVisible(false);
        descriptionTextArea.setVisible(false);

        deleteEntryCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                changeEntryCheckBox.setSelected(false);
                deleteEntryCheckBox.setSelected(true);
                newTimeLabel.setVisible(false);
                descriptionLabel.setVisible(true);
                punktLabel.setVisible(false);
                hourChoiceBox.setVisible(false);
                minuteChoiceBox.setVisible(false);
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
                hourChoiceBox.setVisible(true);
                minuteChoiceBox.setVisible(true);
                descriptionTextArea.setVisible(true);
            }
        });
    }
    @FXML
    protected void changeEntryOnAction(ActionEvent e)
    {

    }
    @FXML
    protected void deleteEntryOnAction(ActionEvent e)
    {

    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
