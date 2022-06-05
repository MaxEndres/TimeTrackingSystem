package com.example.javafx;

import entities.Request;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import utility.DatabaseService;
import utility.Windows;

import java.sql.SQLException;
import java.sql.Time;

public class Requests extends Application {
    @FXML
    public Button acceptButton, denyButton;
    DatabaseService db = new DatabaseService();

    @FXML
    TableView<Request> requestTableView;
    @FXML
    TableColumn<Request, Integer> timestampIdTableColumn;
    @FXML
    TableColumn<Request, Time> newTimeTableColumn;
    @FXML
    TableColumn<Request, String> descriptionTableColumn;
    @FXML
    Label requestIdLabel, newTimeLabel, descriptionLabel, label;
    @FXML
    Button requestButton, backButton;
    @FXML
    AnchorPane message;

    @FXML
    public void initialize() throws SQLException {
        message.setVisible(false);
        ObservableList<Request> requestList = db.listAllRequests();
        requestTableView.setItems(requestList);
        timestampIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestampId"));
        newTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("newTime"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));


    }


    @FXML
    public void requestButtonOnAction(ActionEvent e)
    {
        message.setVisible(true);
        label.setVisible(false);
        Request request = requestTableView.getSelectionModel().getSelectedItem();
        requestIdLabel.setText( "" +request.getTimestampId());
        newTimeLabel.setText(""+ request.getNewTime());
        descriptionLabel.setText(""+ request.getDescription());
    }
    @FXML
    public void denyButtonOnAction(ActionEvent e) throws SQLException {
        Request request = requestTableView.getSelectionModel().getSelectedItem();
        db.denyRequest(request);
        initialize();

    }
    @FXML
    public void acceptButtonOnAction(ActionEvent e) throws SQLException {
        Request request = requestTableView.getSelectionModel().getSelectedItem();
        db.acceptRequest(request);
        initialize();
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
