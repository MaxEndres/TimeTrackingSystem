package com.example.javafx;

import entities.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditUser {
    @FXML
    TextField forename, surname, email, id;
    @FXML
    PasswordField password;
    @FXML
    ComboBox<String> department;
    @FXML
    ComboBox<Integer> targetHours;
    @FXML
    DatePicker startDay;
    @FXML
    Button addUserButton, cancelButton, deleteUserButton;
    @FXML
    Label errorLabel, errorLabelDate, errorLabelEmail;
    @FXML
    CheckBox isAdminCheckBox;
    LocalDate todaysDate = LocalDate.now();
    DatabaseService db = new DatabaseService();

    public EditUser() throws SQLException
    {
    }

    public void initialize()
    {
        forename.setText(SearchUser.editUser.getForename());
        surname.setText(SearchUser.editUser.getSurname());
        email.setText(SearchUser.editUser.getEmail());
        department.setItems(CreateUser.getDepartments());
        department.setValue(SearchUser.editUser.getDepartment());
        targetHours.setItems(CreateUser.getTargetHours());
        targetHours.setValue(SearchUser.editUser.getTargetHours());
        startDay.setValue(SearchUser.editUser.getStartDay().toLocalDate());
        isAdminCheckBox.setSelected(SearchUser.editUser.getIsAdmin());
        errorLabel.setVisible(false);
        errorLabelDate.setVisible(false);
        errorLabelEmail.setVisible(false);
    }
    @FXML
    protected void addUserButtonOnAction(ActionEvent event) throws SQLException, IOException {
        if(forename.getText().isBlank() ||surname.getText().isBlank()|| email.getText().isBlank() )
        {
            errorLabel.setVisible(true);
            errorLabelDate.setVisible(false);
            errorLabelEmail.setVisible(false);

        }else if(startDay.getValue().isAfter(LocalDate.now()))
        {
            errorLabel.setVisible(false);
            errorLabelDate.setVisible(true);
            errorLabelEmail.setVisible(false);
        }else
        {
            Date date= Date.from(Instant.from(startDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            /*
             UserEntity editedUser = new UserEntity(department.getSelectionModel().getSelectedItem(),
                    sqlDate,
                    forename.getText(),
                    surname.getText(),
                    email.getText(),
                    targetHours.getSelectionModel().getSelectedItem(),
                    isAdminCheckBox.isSelected());
             db.updateUser(editedUser);
             *
             */

            db.updateUser(department.getSelectionModel().getSelectedItem(),
                    sqlDate,
                    forename.getText(),
                    surname.getText(),
                    email.getText(),
                    targetHours.getSelectionModel().getSelectedItem(),
                    isAdminCheckBox.isSelected(), SearchUser.editUser.getId());
             Windows.changeWindow(addUserButton, "Admin.fxml");
        }
    }
    @FXML
    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        Windows.changeWindow(cancelButton, "Admin.fxml");
    }
    @FXML
    public void deleteUserButtonOnAction(ActionEvent actionEvent) throws SQLException, IOException {

        db.deleteUser(SearchUser.editUser.getId());
        Windows.changeWindow(deleteUserButton, "Admin.fxml");
    }

}

