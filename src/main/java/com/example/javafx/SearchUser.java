package com.example.javafx;

import entities.UserEntity;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;

public class SearchUser extends Application {
    @FXML
    public TableColumn<UserEntity, Integer> idColumn;
    @FXML
    public TableColumn<UserEntity, String> forenameColumn,surnameColumn,emailColumn;
    @FXML
    public TableColumn<UserEntity, Boolean> isAdminColumn;
    @FXML
    public TableColumn workedHoursColumn;
    public Button viewTimeButton;
    @FXML
    TableView<UserEntity> userTableView;
    @FXML
    TextField searchUserTextField;
    @FXML
    Button cancelButton, editUserButton;
    static UserEntity editUser = null;
    @FXML
    Label errorLabel;
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
        workedHoursColumn.setCellValueFactory(new PropertyValueFactory<>("WorkedHours"));
        errorLabel.setVisible(false);

        FilteredList<UserEntity> userEntityFilteredList = new FilteredList<>(db.listAllUsers(), b -> true);
        searchUserTextField.textProperty().addListener((observableValue, s, t1)  ->
        {
            userEntityFilteredList.setPredicate(User ->
            {
                if (t1.isEmpty() || t1.isBlank()  || t1 == null)
                {
                    return true;
                }
                String keyword = t1.toLowerCase();
                if (User.getSurname().toLowerCase().indexOf(keyword) > -1) {
                    //Means we found a match in First Name
                    return true;
                } else //Means we found a match in email
                    //no match found
                    if (User.getForename().toLowerCase().indexOf(keyword) > -1) {
                    //Means we found a match in Last Name
                    return true;
                } else return User.getEmail().toLowerCase().indexOf(keyword) > -1;
            });

        });
        SortedList<UserEntity> userEntitySortedList = new SortedList<>(userEntityFilteredList);
        //Result with Table View
        userEntitySortedList.comparatorProperty().bind(userTableView.comparatorProperty());
        //Add filters to the table view
        userTableView.setItems(userEntitySortedList);

    }

    @FXML
    private void editUserButtonOnAction(ActionEvent e) throws IOException {
        editUser = userTableView.getSelectionModel().getSelectedItem();
        if(editUser==null)
        {
            errorLabel.setVisible(true);
        }else {
            errorLabel.setVisible(false);
            System.out.println(editUser.getDepartment());
            Windows.changeWindow(editUserButton, "EditUser.fxml");
        }

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

    public void viewTimeButtonOnAction(ActionEvent actionEvent) throws IOException {
        editUser = userTableView.getSelectionModel().getSelectedItem();
        if(editUser==null)
        {
            errorLabel.setVisible(true);
        }else {
            Windows.openWindow("ExportCSVAdmin.fxml");
        }

    }
}
