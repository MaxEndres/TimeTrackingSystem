package com.example.javafx;

import entities.TimestampEntity;
import javafx.application.Application;
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
    public TableColumn<TimestampEntity,Time > timeTableColumn;
    @FXML
    public TableColumn<TimestampEntity, Boolean> isStartTableColumn;
    @FXML
    public TableColumn<TimestampEntity, String> descriptionTableColumn;
    @FXML
    Button changeButton, goBackToProfileButton, addNewButton;
    DatabaseService db= new DatabaseService();
    static TimestampEntity timestamp;

    public EditRequest() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException {
        timestampsTableView.setItems(db.listAllTimestamps(Login.logInUserEntity.getId()));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<TimestampEntity, Date>("date"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timestampIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        isStartTableColumn.setCellValueFactory(new PropertyValueFactory<>("isStart"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        errorLabel.setVisible(false);

    }
@FXML
    protected void changeButtonOnAction(ActionEvent e) throws IOException {
        timestamp = timestampsTableView.getSelectionModel().getSelectedItem();
        if(timestamp==null)
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
        Windows.changeWindow(goBackToProfileButton, "User.fxml");
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
