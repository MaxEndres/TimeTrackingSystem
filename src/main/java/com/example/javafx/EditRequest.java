package com.example.javafx;

import entities.Request;
import entities.Timestamp;
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

public class EditRequest extends Application {

    @FXML
    public TableView<Timestamp> timestampsTableView;
    @FXML
    public TableColumn<Timestamp, Date> dateTableColumn;
    @FXML
    public TableColumn<Timestamp, Integer> timestampIdTableColumn;
    @FXML
    public TableColumn<Timestamp,Time > timeTableColumn;
    @FXML
    public TableColumn<Timestamp, Boolean> isStartTableColumn;
    @FXML
    public TableColumn<Timestamp, String> descriptionTableColumn;
    @FXML
    Button changeButton, goBackToProfileButton, addNewButton;
    DatabaseService db= new DatabaseService();
    static Timestamp timestamp;

    public EditRequest() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException {
        timestampsTableView.setItems(db.listAllTimestamps(Login.logInUser.getId()));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<Timestamp, Date>("date"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timestampIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        isStartTableColumn.setCellValueFactory(new PropertyValueFactory<>("isStart"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    }
@FXML
    protected void changeButtonOnAction(ActionEvent e) throws IOException {
        timestamp = timestampsTableView.getSelectionModel().getSelectedItem();
        Windows.changeWindow(changeButton, "EditConfirmation.fxml");
    }
    @FXML
    protected void addNewButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(addNewButton, "AddRequest.fxml");
    }
    @FXML
    protected void goBackToProfileButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(goBackToProfileButton, "User.fxml");
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
