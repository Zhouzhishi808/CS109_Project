package model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameTimer {
    private Timer timer;
    private int secondsElapsed;
    private JLabel timeLabel;
    private List<TimeUpdateListener> listeners = new ArrayList<>();
    private TimeUpdateListener timeUpdateListener;


    public interface TimeUpdateListener {
        void onTimeUpdate(int seconds);
    }

    public void addTimeUpdateListener(TimeUpdateListener listener) {
        listeners.add(listener);
    }

    public GameTimer(JLabel timeLabel) {
        this.timeLabel = timeLabel;
        this.secondsElapsed = 0;
        this.timer = new Timer(1000, e -> {
            secondsElapsed++;
            updateTimeLabel();
            // 通知GamePanel更新时间
            if (timeUpdateListener != null) {
                timeUpdateListener.onTimeUpdate(secondsElapsed);
            }
        });
    }

    public void updateTimeLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timeLabel.setText(String.format("时间: %02d:%02d", minutes, seconds));

        // 通知监听器
        for (TimeUpdateListener listener : listeners) {
            listener.onTimeUpdate(secondsElapsed);
        }
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

    public void setSecondsElapsed(int seconds) {
        this.secondsElapsed = seconds;
        updateTimeLabel();
    }

    public void continueFrom(int seconds) {
        pause();
        setSecondsElapsed(seconds);
        start();
    }

    public void setTimeUpdateListener(TimeUpdateListener listener) {
        this.timeUpdateListener = listener;
    }
}
