package com.example.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class UserProfile extends Application  {
    @FXML
    Label timeLabel;
    @FXML
    Button startButton, pauseButton, stopButton;

    int seconds=0;
    int minutes=0;
    int hours=0;
    int time=0;
    boolean started = false;
    String hour, minute, second;

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


    }
    @FXML
    protected void stopButtonOnAction(ActionEvent event)
    {}

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


    }


}
