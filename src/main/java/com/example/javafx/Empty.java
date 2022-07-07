package com.example.javafx;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utility.Windows;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static utility.Export.exportAllTimeStamps;

public class Empty extends Application {
    public ComboBox monthComboBox;
    public ComboBox yearComboBox;
    public Button exportButton;
    public Button goBackButton;
    @FXML
    Label emptyLabel;

    @FXML
    void initialize()
    {
        monthComboBox.setItems(getMonths());
        yearComboBox.setItems(getYears());
        monthComboBox.getSelectionModel().select("July");
        yearComboBox.getSelectionModel().select("2022");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void goBackButtonOnAction(ActionEvent actionEvent)
    {
        Windows.closeWindow(goBackButton);
    }

    public void exportButtonOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        exportAllTimeStamps(Login.logInUserEntity.getId());
        HostServices doc = getHostServices();
        LocalDate today = LocalDate.now();
        // file name
        doc.showDocument("C:\\Users\\Public\\Downloads\\timestamp"+ today + ".csv");

    }
    public static ObservableList<String> getMonths()
    {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January", "February","March","April","May", "June", "July", "August", "September", "October", "November", "December" );
        return months;
    }
    public static ObservableList<Integer> getYears()
    {
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for(int i = 2000; i<= LocalDate.now().getYear(); i++)
        {
            years.add(i);
        }
        return years;
    }
}
