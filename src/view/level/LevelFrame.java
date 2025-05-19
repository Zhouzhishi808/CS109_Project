package view.level;
/*
在这个文件中完成关卡选择功能
 */

import controller.MusicController;
import model.Constants;
import model.MapModel;
import model.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LevelFrame extends JFrame {
    //界面初始化
    private GameFrame gameFrame;
    private LoginFrame loginFrame;

    private final JButton level1Button;
    private final JButton level2Button;
    private final JButton level3Button;
    private final JButton level4Button;
    private final JButton level5Button;
    private final JButton returnBtn;

    private User currentUser;
    private MapModel mapModel;

    public LevelFrame(int width, int height, LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.setTitle("选择关卡");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);



        ImageIcon exitIcon = new ImageIcon("Picture/buttonPic/exitBtn.png");
        ImageIcon level1Icon = new ImageIcon("Picture/buttonPic/level1Btn.png");
        ImageIcon level2Icon = new ImageIcon("Picture/buttonPic/level2Btn.png");
        ImageIcon level3Icon = new ImageIcon("Picture/buttonPic/level3Btn.png");
        ImageIcon level4Icon = new ImageIcon("Picture/buttonPic/level4Btn.png");
        ImageIcon level5Icon = new ImageIcon("Picture/buttonPic/level5Btn.png");
        ImageIcon level1PressedIcon = new ImageIcon("Picture/buttonPic/level1PressedBtn.png");
        ImageIcon level2PressedIcon = new ImageIcon("Picture/buttonPic/level2PressedBtn.png");
        ImageIcon level3PressedIcon = new ImageIcon("Picture/buttonPic/level3PressedBtn.png");
        ImageIcon level4PressedIcon = new ImageIcon("Picture/buttonPic/level4PressedBtn.png");
        ImageIcon level5PressedIcon = new ImageIcon("Picture/buttonPic/level5PressedBtn.png");
        ImageIcon returnPressedIcon = new ImageIcon("Picture/buttonPic/exitPressedBtn.png");
        ImageIcon levelFrameIcon = new ImageIcon("Picture/framePic/levelFrame.png");

        JLabel backgroundLabel = new JLabel(levelFrameIcon);
        backgroundLabel.setLayout(null);
        this.setContentPane(backgroundLabel);

        level1Button = FrameUtil.createButton(this, level1Icon, new Point(160,50), 146,51);
        level1Button.setBorderPainted(false);
        level1Button.setContentAreaFilled(false);
        level1Button.setFocusPainted(false);
        level2Button = FrameUtil.createButton(this, level2Icon, new Point(163,130), 137, 50);
        level2Button.setBorderPainted(false);
        level2Button.setContentAreaFilled(false);
        level2Button.setFocusPainted(false);
        level3Button = FrameUtil.createButton(this, level3Icon, new Point(163,210), 143, 50);
        level3Button.setBorderPainted(false);
        level3Button.setContentAreaFilled(false);
        level3Button.setFocusPainted(false);
        level4Button = FrameUtil.createButton(this, level4Icon, new Point(167,290), 134, 56);
        level4Button.setBorderPainted(false);
        level4Button.setContentAreaFilled(false);
        level4Button.setFocusPainted(false);
        level5Button = FrameUtil.createButton(this, level5Icon, new Point(165,370), 144, 52);
        level5Button.setBorderPainted(false);
        level5Button.setContentAreaFilled(false);
        level5Button.setFocusPainted(false);
        returnBtn = FrameUtil.createButton(this, exitIcon, new Point(40,40), 90, 34);
        returnBtn.setBorderPainted(false);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setFocusPainted(false);

        mapModel = new MapModel();

        level1Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                level1Button.setIcon(level1PressedIcon);
                level1Button.setLocation(160, 52);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                level1Button.setLocation(160,50);
                level1Button.setIcon(level1Icon);
                Level1();
                mapModel.setName("横刀立马");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                    gameFrame.setTitle("兵分三路 当前用户:" + currentUser.getUsername());
                }
                MusicController.playBackgroundMusic("Music/BGM/level1.wav");
            }
        });

        level2Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                level2Button.setIcon(level2PressedIcon);
                level2Button.setLocation(163, 134);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                level2Button.setLocation(163,132);
                level2Button.setIcon(level2Icon);
                Level2();
                mapModel.setName("层层设防");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame( 800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                    gameFrame.setTitle(currentUser.getUsername());
                }
                MusicController.playBackgroundMusic("Music/BGM/level2.wav");
            }
        });

        level3Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                level3Button.setIcon(level3PressedIcon);
                level3Button.setLocation(163, 212);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                level3Button.setLocation(163,210);
                level3Button.setIcon(level3Icon);
                Level3();
                mapModel.setName("四将连关");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                    gameFrame.setTitle(currentUser.getUsername());
                }
                MusicController.playBackgroundMusic("Music/BGM/level3.wav");
            }
        });

        level4Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                level4Button.setIcon(level4PressedIcon);
                level4Button.setLocation(167, 292);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                level4Button.setLocation(167,290);
                level4Button.setIcon(level4Icon);
                Level4();
                mapModel.setName("水泄不通");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                    gameFrame.setTitle(currentUser.getUsername());
                }
                MusicController.playBackgroundMusic("Music/BGM/level4.wav");
            }
        });

        level5Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                level5Button.setIcon(level5PressedIcon);
                level5Button.setLocation(165, 372);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                level5Button.setLocation(165,370);
                level5Button.setIcon(level5Icon);
                Level5();
                mapModel.setName("兵分三路");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                    gameFrame.setTitle(currentUser.getUsername());
                }
                MusicController.playBackgroundMusic("Music/BGM/level5.wav");
            }
        });

        returnBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                returnBtn.setIcon(returnPressedIcon);
                returnBtn.setLocation(40, 42);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                returnBtn.setLocation(40,40);
                returnBtn.setIcon(exitIcon);
                returnToLogin();
                MusicController.stopBackgroundMusic();
                MusicController.playBackgroundMusic("Music/BGM/loginFrame.wav");
            }
        });

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    private void Level1() {//横刀立马
        int [][] map = {
                {0,0,0,0},
                {0,1,1,0},
                {0,5,6,0},
                {0,5,6,0},
                {0,0,0,0},
//                {6,9,9,7},
//                {6,9,9,7},
//                {5,2,2,8},
//                {5,1,1,8},
//                {1,0,0,1},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level2() {//层层设防
        int [][] map = {
                {1,9,9,1},
                {1,9,9,1},
                {6,7,3,3},
                {6,7,4,4},
                {0,2,2,0},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level3() {//四将连关
        int [][] map = {
                {9,9,2,2},
                {9,9,3,3},
                {6,7,4,4},
                {6,7,1,1},
                {1,0,0,1},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level4() {//水泄不通
        int [][] map = {
                {6,9,9,1},
                {6,9,9,1},
                {2,2,3,3},
                {2,2,4,4},
                {1,0,0,1},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level5() {//兵分三路
        int [][] map = {
                {1,9,9,1},
                {5,9,9,8},
                {5,2,2,8},
                {6,1,1,7},
                {6,0,0,7},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void returnToLogin() {
        this.dispose(); // 销毁当前游戏窗口
        loginFrame.setVisible(true);// 显示关卡选择界面
    }
}