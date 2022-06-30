package com.example.javafx;

import entities.RequestEntity;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class PendingRequests extends Application {

    public TableView<RequestEntity> requestTableView;
    public TableColumn<RequestEntity, Integer> requestIdTableColumn;
    public TableColumn<RequestEntity, Date> dateTableColumn;
    public TableColumn<RequestEntity, Time> startTableColumn;
    public TableColumn<RequestEntity, Time> stopTableColumn;
    public TableColumn<RequestEntity, String> statusTableColumn;
    public Button goBackButton;
    DatabaseService db = new DatabaseService();

    public PendingRequests() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException {
        requestTableView.setItems(db.listAllRequests(Login.logInUserEntity.getId()));
        requestIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestampId"));
        //dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTableColumn.setCellValueFactory(new PropertyValueFactory<>("newTimeStart"));
        stopTableColumn.setCellValueFactory(new PropertyValueFactory<>("newTimeStop"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }
    @FXML
    public void goBackButtonOnAction(ActionEvent e) throws IOException {
        Windows.closeWindow(goBackButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
