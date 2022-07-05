package com.example.javafx;

import entities.TimestampEntity;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class EditRequest extends Application {
    @FXML
    Label errorLabel;
    @FXML
    public TableView<TimestampEntity> timestampsTableView;
    @FXML
    public TableColumn<TimestampEntity, Date> dateTableColumn;
    @FXML
    public TableColumn<TimestampEntity, Integer> timestampIdTableColumn;
    @FXML
    public TableColumn<TimestampEntity,Time > stopTableColumn;
    @FXML
    public TableColumn<TimestampEntity, Time> startTableColumn;
    @FXML
    Button changeButton, goBackToProfileButton, addNewButton;
    DatabaseService db= new DatabaseService();
    static TimestampEntity timestamp;

    public EditRequest() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException {

        ObservableList<TimestampEntity> list = db.listAllTimestamps(Login.logInUserEntity.getId());
        timestampsTableView.setItems(list);
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTableColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        stopTableColumn.setCellValueFactory(new PropertyValueFactory<>("stop"));
        timestampIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        errorLabel.setVisible(false);
        timestamp = timestampsTableView.getSelectionModel().getSelectedItem();


    }
@FXML
    protected void changeButtonOnAction(ActionEvent e) throws IOException, SQLException {
        timestamp = timestampsTableView.getSelectionModel().getSelectedItem();
        if(db.checkRequestTable(timestamp.getId()))
        {

        }if(timestamp==null)
        {
            errorLabel.setVisible(true);
        }else
        {
            Windows.changeWindow(changeButton, "EditConfirmation.fxml");
        }
    }
    @FXML
    protected void addNewButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(addNewButton, "AddRequest.fxml");
    }
    @FXML
    protected void goBackToProfileButtonOnAction(ActionEvent e) throws IOException {
        Windows.closeWindow(goBackToProfileButton);
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
