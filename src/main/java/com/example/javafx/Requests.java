package com.example.javafx;

import entities.Request;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.DatabaseService;

import java.sql.SQLException;
import java.sql.Time;

public class Requests extends Application {
    DatabaseService db = new DatabaseService();
    ObservableList<Request> requestList = db.listAllRequests();
    @FXML
    TableView<Request> requestTableView;
    @FXML
    TableColumn<Request, Integer> timestampIdTableColumn;
    @FXML
    TableColumn<Request, Time> newTimeTableColumn;
    @FXML
    TableColumn<Request, String> descriptionTableColumn;

    @FXML
    public void initialize()
    {

        requestTableView.setItems(requestList);/*
        timestampIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("timestampId"));
        newTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("newTime"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

         */
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
