package com.example.javafx;

import entities.Hashing;
import entities.User;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CreateUser extends Application {
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
    Button addUserButton, cancelButton;
    @FXML
    Label errorLabel;
    @FXML
    CheckBox isAdminCheckBox;
    LocalDate todaysDate = LocalDate.now();
    DatabaseService db = new DatabaseService();

    public CreateUser() throws SQLException {
    }

    @FXML
    public void initialize()
    {
        final String[] forenameText = new String[1];
        final String[] surnameText = new String[1];

        department.setItems(getDepartments());
        department.getSelectionModel().select(0);
        startDay.setValue(todaysDate);
        targetHours.setItems(getTargetHours());
        targetHours.getSelectionModel().select(5);
        errorLabel.setVisible(false);

        forename.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                surname.textProperty().addListener(new ChangeListener<String>() {

                    public void changed(ObservableValue<? extends String> observableValue, String s2, String t2) {
                        email.setText(t1.toLowerCase()+t2.toLowerCase() + "@onpoint.de");

                    }
                });
            }
        });


        //startDay.setValue();
        //toDo: id sollte automatisch
        //toDo: email, domain von der Firma?
    }

    @FXML
    protected void addUserButtonOnAction(ActionEvent event) throws IOException, SQLException {
        if(forename.getText().isBlank() ||surname.getText().isBlank()|| email.getText().isBlank() )
        {
            errorLabel.setVisible(true);

        }else
        {
            //
            // TODO: Konstruktor statt setter verwenden
            //TODO: Salt auch speichern in den Datenbank
            //Todo: Password ohne Salt speicher NUR zum pdf funktion! und später auf null

            String salt = BCrypt.gensalt();

            // NUR FÜR EXPORT FUNKTION!!! passwordOhneSalt NICHT SPEICHERN!!!
            String passwordOhneSalt= Hashing.genPassword();

            String passwordMitSalt = BCrypt.hashpw(passwordOhneSalt,salt);

            Date date= Date.from(startDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            User createdUser = new User(department.getSelectionModel().getSelectedItem(),
                    sqlDate,
                    forename.getText(),
                    surname.getText(),
                    email.getText(),
                    passwordMitSalt,
                    salt,
                    targetHours.getSelectionModel().getSelectedItem(),
                    isAdminCheckBox.isSelected());


            //toDo: create user with password and salt etc
           // db.createUser(createdUser);

            //TODO: password automatisch

            //TODO: send email?
        }
    }

    @FXML
    private void cancelButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(cancelButton, "Admin.fxml");
    }

    public static ObservableList<String> getDepartments()
    {
        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.addAll("Marketing", "Operations", "Finance", "Sales", "Human Resources", "Purchase");
        return departments;
    }
    public static ObservableList<Integer> getTargetHours()
    {
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        hours.addAll(1,2,3,4,5,6,7,8,9,10);
        return hours;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
