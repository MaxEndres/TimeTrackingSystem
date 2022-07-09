package com.example.javafx;

import entities.RequestEntity;
import entities.TimestampEntity;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.sql.SQLException;
import java.sql.Time;

public class Requests extends Application {
    @FXML
    public Button acceptButton, denyButton;
    public Label oldTimeLabel;
    public Label newDateLabel1;
    public Label newDateLabel;
    public Label oldTimeLabelOut;
    public Label oldDate;
    DatabaseService db = new DatabaseService();

    @FXML
    TableView<RequestEntity> requestTableView;
    @FXML
    TableColumn<RequestEntity, Integer> timestampIdTableColumn, userIdTableColumn;
    @FXML
    TableColumn<RequestEntity, Time> newTimeTableColumn;
    @FXML
    TableColumn<RequestEntity, String> typeTableColumn;
    @FXML
    Label requestIdLabel,newTimeLabel1,errorLabel, timeLabel, descriptionLabel, label, dateLabel, userLabel;
    @FXML
    Button requestButton, backButton;
    @FXML
    AnchorPane message;

    @FXML
    public void initialize() throws SQLException {
        message.setVisible(false);
        errorLabel.setVisible(false);
        ObservableList<RequestEntity> requestEntityList = db.listAllRequests("PENDING");
        requestTableView.setItems(requestEntityList);
        timestampIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestampId"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        userIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }


    @FXML
    public void requestButtonOnAction(ActionEvent e) throws SQLException {
        RequestEntity requestEntity = requestTableView.getSelectionModel().getSelectedItem();
        if(requestEntity== null)
        {
            errorLabel.setVisible(true);
        }else
        {
            message.setVisible(true);
            label.setVisible(false);
            errorLabel.setVisible(false);
            requestIdLabel.setText("" +requestEntity.getType());
            TimestampEntity timestampRequest = db.getTimestamp(requestEntity.getTimestampId());

            if(requestEntity.getType().equals("ADD_NEW"))
            {
                dateLabel.setVisible(false);
                oldDate.setVisible(false);
                oldTimeLabelOut.setVisible(false);
                oldTimeLabel.setVisible(false);

                newDateLabel.setText(timestampRequest.getDate().toString());
                timeLabel.setText("FROM "+ requestEntity.getNewTimeStart()+ " TO "+ requestEntity.getNewTimeStop());

            }else if(requestEntity.getType().equals("UPDATE"))
            {
                oldDate.setText(timestampRequest.getDate().toString());
                oldTimeLabel.setText("FROM "+ timestampRequest.getStart()+ " TO "+timestampRequest.getStop());

                newDateLabel.setVisible(false);
                newDateLabel1.setVisible(false);
                timeLabel.setText("FROM "+ requestEntity.getNewTimeStart()+ " TO "+ requestEntity.getNewTimeStop());

            }else if(requestEntity.getType().equals("DELETE"))
            {
                newDateLabel.setVisible(false);
                newDateLabel1.setVisible(false);
                newTimeLabel1.setVisible(false);
                timeLabel.setVisible(false);
                oldDate.setText(timestampRequest.getDate().toString());
                oldTimeLabel.setText("FROM "+ timestampRequest.getStart()+ " TO "+timestampRequest.getStop() );

            }
            userLabel.setText(db.nameOfUser(requestEntity.getUserId())+ " (ID: "+ requestEntity.getUserId()+")");
            descriptionLabel.setText(""+ requestEntity.getDescription());
        }
    }
    @FXML
    public void denyButtonOnAction(ActionEvent e) throws SQLException {
        RequestEntity requestEntity = requestTableView.getSelectionModel().getSelectedItem();
        db.denyRequest(requestEntity);
        initialize();
        requestEntity = null;

    }
    @FXML
    public void acceptButtonOnAction(ActionEvent e) throws SQLException {
        RequestEntity requestEntity = requestTableView.getSelectionModel().getSelectedItem();
        if(requestEntity.getType().equals("ADD"))
        {
            //db.insertTimestamp();
        }
        db.acceptRequest(requestEntity);
        initialize();
        requestEntity= null;
    }
    @FXML
    public void backButtonOnAction(ActionEvent e)
    {
        Windows.closeWindow(backButton);
    }

    public Requests() throws SQLException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
