package com.example.javafx;

import entities.RequestEntity;
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
    Label requestIdLabel, timeLabel, descriptionLabel, label, dateLabel, userLabel;
    @FXML
    Button requestButton, backButton;
    @FXML
    AnchorPane message;

    @FXML
    public void initialize() throws SQLException {
        message.setVisible(false);
        ObservableList<RequestEntity> requestEntityList = db.listAllRequests("PENDING");
        requestTableView.setItems(requestEntityList);
        timestampIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestampId"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        userIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }


    @FXML
    public void requestButtonOnAction(ActionEvent e) throws SQLException {
        message.setVisible(true);
        label.setVisible(false);
        RequestEntity requestEntity = requestTableView.getSelectionModel().getSelectedItem();
        requestIdLabel.setText("" +requestEntity.getType());
        userLabel.setText(db.nameOfUser(requestEntity.getUserId())+ " (ID: "+ requestEntity.getUserId()+")");

        timeLabel.setText("FROM "+ requestEntity.getNewTimeStart()+ " TO "+ requestEntity.getNewTimeStop());
        descriptionLabel.setText(""+ requestEntity.getDescription());
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
