package controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicController {
    // 分离背景音乐和音效的 Clip
    private static Clip backgroundMusic;
    private static Clip soundEffect;

    // 播放背景音乐（不停止音效）
    public static void playBackgroundMusic(String path) {
        try {
            stopBackgroundMusic(); // 停止之前的背景音乐
            backgroundMusic = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            backgroundMusic.open(ais);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // 循环播放
            backgroundMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 播放音效（不停止背景音乐）
    public static void playSoundEffect(String path) {
        try {
            if (soundEffect != null) {
                soundEffect.stop(); // 停止之前的音效
            }
            soundEffect = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            soundEffect.open(ais);
            soundEffect.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 停止所有背景音乐
    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.close();
            backgroundMusic = null;
        }
    }

    // 停止所有音效
    public static void stopSoundEffects() {
        if (soundEffect != null) {
            soundEffect.stop();
            soundEffect.close();
            soundEffect = null;
        }
    }

    // 点击音效
    public static void playClickSound() {
        playSoundEffect("Music/sound/click.wav");
    }

    // 移动音效
    public static void playMoveSound() {
        playSoundEffect("Music/sound/move.wav");
    }

    public static void playVictorySound() {
        playSoundEffect("Music/sound/victory.wav");
    }
}