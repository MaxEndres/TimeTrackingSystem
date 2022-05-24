package com.example.javafx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.Windows;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class UserProfile extends Application  {
    @FXML
    Label timeLabel;
    @FXML
    Button startButton, pauseButton, stopButton;
    @FXML
    AnchorPane pane;
    Timeline timeline;
    LocalTime time= LocalTime.parse("00:00:00");
    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("HH:mm:ss");
    /*int seconds=0;
    int minutes=0;
    int hours=0;
    int time=0;
    boolean started = false;
    String hour, minute, second;
    */
    @FXML
    public void initialize()
    {
        timeLabel.setText(time.format(dtf));
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void incrementTime()
    {
        time= time.plusSeconds(1);
        timeLabel.setText(time.format(dtf));
    }
/*
    Timer timer = new Timer(1000, new ActionListener() { //miliseconds

        public void actionPerformed(ActionEvent e)
        {
            time = time+1000;
            hours= time/3600000;
            minutes = (time/60000)%60;
            seconds= (time/1000)%60;
            second = String.format("%02d", seconds);
            minute = String.format("%02d", minutes);
            hour = String.format("%02d", hours);
            timeLabel.setText(hour+":"+minute+":"+second);
        }
    });

    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(e.getSource()==startButton)
        {
            timer.start();
        }
    }

 */
    @FXML
    protected void startButtonOnAction(ActionEvent event)
    {

        timeline.play();
    }
    @FXML
    protected void pauseButtonOnAction(ActionEvent event)
    {
        if(timeline.getStatus().equals(Animation.Status.PAUSED))
        {
            pauseButton.setText("Pause");
            timeline.play();
            //change button
        }else if(timeline.getStatus().equals(Animation.Status.RUNNING))
        {
            pauseButton.setText("Continue");
            timeline.pause();
            //Change button
        }
    }
    @FXML
    protected void stopButtonOnAction(ActionEvent event)
    {

        //save entry in the database
    }
    @FXML
    protected void logOutOnAction(ActionEvent event) throws IOException {
        Windows.changeWindow(stopButton, "hello-view.fxml");
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
