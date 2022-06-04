package com.example.javafx;

import entities.User;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.DatabaseService;

import java.sql.SQLException;

public class SearchUser extends Application {
    @FXML
    public TableColumn<User, Integer> idColumn;
    @FXML
    public TableColumn<User, String> forenameColumn,surnameColumn,emailColumn;
    @FXML
    public TableColumn<User, Boolean> isAdminColumn;
    @FXML
    TableView<User> userTableView;
    @FXML
    TextField searchUserTextField;
    DatabaseService db = new DatabaseService();

    public SearchUser() throws SQLException {
    }

    @FXML
    protected void initialize() throws SQLException {
        userTableView.setItems(db.listAllUsers());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        //toDo: isAdmin
        FilteredList<User> patientFilteredList = new FilteredList<>(db.listAllUsers(), b -> true);
        searchUserTextField.textProperty().addListener((observableValue, s, t1)  ->
        {
            patientFilteredList.setPredicate(Patient ->
            {
                if (t1.isEmpty() || t1.isBlank()  || t1 == null)
                {
                    return true;
                }
                String keyword = t1.toLowerCase();
                if (User.getSurname().toLowerCase().indexOf(keyword) > -1) {
                    //Means we found a match in First Name
                    return true;
                } else if (User.getForename().toLowerCase().indexOf(keyword) > -1) {
                    //Means we found a match in Last Name
                    return true;
                } else if (User.getEmail().toLowerCase().indexOf(keyword) > -1) {
                    //Means we found a match in email
                    return true;
                }  else {
                    //no match found
                    return false;
                }
            });

        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
