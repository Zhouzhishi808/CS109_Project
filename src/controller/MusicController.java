package controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicController {
    private Clip music = null;
    private File sourceFile = null;

    public MusicController(String path) {
        try {
            music = AudioSystem.getClip(); // 获取可用于播放音频文件或音频流的数据流
            sourceFile = new File(path);//获取文件
            AudioInputStream ais = AudioSystem.getAudioInputStream(sourceFile);//获得指示格式的音频输入流
            music.open(ais); //打开数据流
            music.start();    //开始播放音乐
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (music != null) {
            music.stop();
        }
    }

    public void startMusic(){
        music.setFramePosition(0);
        music.start();
    }

    public static void playMoveSound() {
        MusicController musicController = new MusicController("Music/sound/move.wav");
    }

    public static void playVictorySound() {
        MusicController musicController = new MusicController("Music/sound/victory.wav");
    }

    public static void playLoseSound() {
        MusicController musicController = new MusicController("Music/sound/lose.wav");
    }

    public static void playClickSound() {
        MusicController musicController = new MusicController("Music/sound/click.wav");
    }

    public static void playLevel1Sound() {
        MusicController musicController = new MusicController("Music/BGM/level1.wav");
    }

    public static void playLevel2Sound() {
        MusicController musicController = new MusicController("Music/BGM/level2.wav");
    }

    public static void playLevel3Sound() {
        MusicController musicController = new MusicController("Music/BGM/level3.wav");
    }

    public static void playLevel4Sound() {
        MusicController musicController = new MusicController("Music/BGM/level4.wav");
    }

    public static void playLevel5Sound() {
        MusicController musicController = new MusicController("Music/BGM/level5.wav");
    }
}
