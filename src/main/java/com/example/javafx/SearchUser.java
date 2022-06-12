package com.example.javafx;

import entities.User;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;

public class SearchUser extends Application {
    @FXML
    public TableColumn<User, Integer> idColumn;
    @FXML
    public TableColumn<User, String> forenameColumn,surnameColumn,emailColumn;
    @FXML
    public TableColumn<User, Boolean> isAdminColumn;
    @FXML
    public TableColumn workedHoursColumn;
    @FXML
    TableView<User> userTableView;
    @FXML
    TextField searchUserTextField;
    @FXML
    Button cancelButton, editUserButton;
    DatabaseService db = new DatabaseService();

    public SearchUser() throws SQLException {
    }

    @FXML
    protected void initialize() throws SQLException {
        userTableView.setItems(db.listAllUsers());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        forenameColumn.setCellValueFactory(new PropertyValueFactory<>("Forename"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("IsAdmin"));
        workedHoursColumn.setCellValueFactory(new PropertyValueFactory<>("IsAdmin"));
        //toDo: isAdmin

        FilteredList<User> userFilteredList = new FilteredList<>(db.listAllUsers(), b -> true);
        searchUserTextField.textProperty().addListener((observableValue, s, t1)  ->
        {
            userFilteredList.setPredicate(User ->
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
        SortedList<User> userSortedList= new SortedList<>(userFilteredList);
        //Result with Table View
        userSortedList.comparatorProperty().bind(userTableView.comparatorProperty());
        //Add filters to the table view
        userTableView.setItems(userSortedList);

    }

    @FXML
    private void editUserButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(editUserButton, "EditUser.fxml");
    }
    @FXML
    private void cancelButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(cancelButton, "Admin.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
