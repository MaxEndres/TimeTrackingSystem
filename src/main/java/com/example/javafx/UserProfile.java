package com.example.javafx;

import entities.TimestampEntity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.DatabaseService;
import utility.Windows;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UserProfile extends Application  {
    @FXML
    Label timeLabel, workedTimeLabel;
    @FXML
    Button startButton, stopButton, editTimeButton, pendingRequestButton;
    @FXML
    AnchorPane pane;
    @FXML
    MenuButton nameMenuButton;
    Timeline timeline;
    LocalTime time= LocalTime.parse("00:00:00");
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("HH:mm:ss");
    DatabaseService db = new DatabaseService();
    TimestampEntity timestamp;
    Date startDate;
    Time startTime;

    public UserProfile() throws SQLException {
    }

    @FXML
    public void initialize()
    {
        timeLabel.setText(time.format(dtf));
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        stopButton.setVisible(false);
        nameMenuButton.setText(Login.logInUserEntity.getForename() +" "+ Login.logInUserEntity.getSurname());
    }

    private void incrementTime()
    {
        time= time.plusSeconds(1);
        timeLabel.setText(time.format(dtf));
    }

    @FXML
    protected void startButtonOnAction(ActionEvent event) throws SQLException {

        timeline.play();
        startButton.setVisible(false);
        stopButton.setVisible(true);
        workedTimeLabel.setVisible(false);

        //CREATE TIMESTAMP
        /*
        TimestampEntity timestamp = new TimestampEntity(Login.logInUserEntity.getId(),
                java.sql.Date.valueOf(LocalDate.now())
                ,java.sql.Time.valueOf(LocalTime.now()),
                true,"" );
        db.createTimestamp(timestamp);

         */
        startDate= java.sql.Date.valueOf(LocalDate.now());
        startTime= java.sql.Time.valueOf(LocalTime.now());
        timestamp = new TimestampEntity(Login.logInUserEntity.getId(),
                startTime, startDate);
        db.insertTimestamp(timestamp);
    }


    @FXML
    protected void stopButtonOnAction(ActionEvent event) throws SQLException {
        stopButton.setVisible(false);
        startButton.setVisible(true);
        if(timeline.getStatus().equals(Animation.Status.PAUSED))
        {
            timeline.play();
        }else if(timeline.getStatus().equals(Animation.Status.RUNNING))
        {
            timeline.pause();
            workedTimeLabel.setVisible(true);
            workedTimeLabel.setText("Submitted time: "+ timeLabel.getText());
            time= LocalTime.parse("00:00:00");
            timeLabel.setText(time.format(dtf));

            //save entry in the database//Change button
        }

        Time stopTime = java.sql.Time.valueOf(LocalTime.now());

       // timestamp = new TimestampEntity(Login.logInUserEntity.getId(),
         //       startTime, stopTime,startDate);
        db.updateTimestamp(stopTime);


    }
    @FXML
    protected void logOutOnAction(ActionEvent event) throws IOException {
        //TODO: Confirmation
        Login.logInUserEntity = null;
        Windows.changeWindow(stopButton, "hello-view.fxml");
    }
    @FXML
    protected void pendingRequestButtonOnAction(ActionEvent e) throws IOException {
        Windows.openWindow("PendingRequests.fxml");
    }
    @FXML
    protected void editTimeButtonOnAction(ActionEvent event) throws IOException {
        Windows.openWindow( "EditRequest.fxml");
    }
    @FXML
    protected void changePasswordOnAction(ActionEvent event) throws IOException {
        Windows.loadWindow("ChangePassword.fxml", pane);
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


    }


}
