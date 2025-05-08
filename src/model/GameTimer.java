package model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    private Timer timer;
    private int secondsElapsed;
    private JLabel timeLabel;

    public GameTimer(JLabel timeLabel) {
        this.timeLabel = timeLabel;
        this.secondsElapsed = 0;
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                updateTimeLabel();
            }
        });
    }

    private void updateTimeLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timeLabel.setText(String.format("时间: %02d:%02d", minutes, seconds));
    }

    public void start() {
        timer.start();
    }

    public void pause() {
        timer.stop();
    }

    public void reset() {
        timer.stop();
        secondsElapsed = 0;
        updateTimeLabel();
    }

    public int getTimeInSeconds() {
        return secondsElapsed;
    }
}
