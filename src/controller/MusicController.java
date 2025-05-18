package controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicController {
    private Clip music;
    private File sourceFile = null;

    public MusicController() {}

    public void stopMusic() {
        if (music != null) {
            music.stop();   // 停止播放
            music.close();  // 释放资源
            music = null;   // 清除引用
        }
        System.out.println("Music stopped");
    }

    public void startMusic(String path) {
        try {
            music = AudioSystem.getClip(); // 获取可用于播放音频文件或音频流的数据流
            sourceFile = new File(path);//获取文件
            AudioInputStream ais = AudioSystem.getAudioInputStream(sourceFile);//获得指示格式的音频输入流
            music.open(ais); //打开数据流
            music.setFramePosition(0);
            music.start();    //开始播放音乐
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playClickSound() {
        startMusic("Music/sound/click.wav");
    }

    public void playMoveSound() {
        startMusic("Music/sound/move.wav");
    }
}
