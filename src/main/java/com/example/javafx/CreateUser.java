package com.example.javafx;

import com.itextpdf.text.DocumentException;
import utility.Export;
import utility.Hashing;
import entities.UserEntity;
import javafx.application.Application;
import javafx.application.HostServices;
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
import java.util.Date;

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
    Label errorLabel, errorLabelDate, errorLabelEmail;
    @FXML
    CheckBox isAdminCheckBox;
    LocalDate todaysDate = LocalDate.now();
    DatabaseService db = new DatabaseService();

    public CreateUser() throws SQLException {
    }

    @FXML
    public void initialize()
    {
        department.setItems(getDepartments());
        department.getSelectionModel().select(0);
        startDay.setValue(todaysDate);
        targetHours.setItems(getTargetHours());
        targetHours.getSelectionModel().select(5);
        errorLabel.setVisible(false);
        errorLabelDate.setVisible(false);
        errorLabelEmail.setVisible(false);
        //startDay.setValue();
        //toDo: id sollte automatisch
        //toDo: email, domain von der Firma?
    }

    @FXML
    protected void addUserButtonOnAction(ActionEvent event) throws IOException, SQLException, DocumentException {
        if(forename.getText().isBlank() ||surname.getText().isBlank()|| email.getText().isBlank() )
        {
            errorLabel.setVisible(true);
            errorLabelDate.setVisible(false);
            errorLabelEmail.setVisible(false);

        }/*else if(startDay.getValue().isAfter(LocalDate.now()))
        {
            errorLabel.setVisible(false);
            errorLabelDate.setVisible(true);
            errorLabelEmail.setVisible(false);
        }*/else if(!db.checkEmail(email.getText()))
        {
            errorLabel.setVisible(false);
            errorLabelDate.setVisible(false);
            errorLabelEmail.setVisible(true);
        }else
        {

            String salt = BCrypt.gensalt();
            String passwordOhneSalt= Hashing.genPassword();
            String passwordMitSalt = BCrypt.hashpw(passwordOhneSalt,salt);

            //TEST
            System.out.println(passwordOhneSalt);
            //PARSE FROM DATE TO SQL DATE
            Date date= Date.from(startDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            //CREATE USER
            UserEntity createdUserEntity = new UserEntity(department.getSelectionModel().getSelectedItem(),
                    sqlDate,
                    forename.getText(),
                    surname.getText(),
                    email.getText(),
                    passwordMitSalt,
                    salt,
                    targetHours.getSelectionModel().getSelectedItem(),
                    isAdminCheckBox.isSelected(), true);
            db.createUser(createdUserEntity);

            //PDF EXPORT
            HostServices doc = getHostServices();
            Export.exportPWasPDF(createdUserEntity, passwordOhneSalt);
            doc.showDocument("C:\\Users\\Public\\Downloads\\" +
                    db.getMaxID() + "credentials" + ".pdf");
            passwordOhneSalt="x";
            //CHANGE WINDOWPdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
            //                "C:\\Users\\Public\\Downloads\\" +
            //                        databaseService.getMaxID() + "credentials" + ".pdf"));
            Windows.changeWindow(addUserButton, "Admin.fxml");

        }
    }

    @FXML
    private void cancelButtonOnAction(ActionEvent e) throws IOException {
        Windows.changeWindow(cancelButton, "Admin.fxml");
    }

    public static ObservableList<String> getDepartments()
    {
        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.addAll("Help Desk", "IT", "Sales", "Marketing", "Human Resource Management", "Research and Development");
        return departments;
    }
    public static ObservableList<Integer> getTargetHours()
    {
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        hours.addAll(10,15,20,25,30,35,40);
        return hours;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
