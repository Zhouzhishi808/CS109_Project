package view.game;

import controller.GameController;
import controller.MusicController;
import model.Constants;
import model.GameTimer;
import model.MapModel;
import model.User;
import view.FrameUtil;
import view.level.LevelFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class GameFrame extends JFrame {
    private LevelFrame levelFrame;

    private GameController controller;
    private GameTimer gameTimer;

    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton exitBtn;
    private JButton returnBtn;
    private JButton leaderboardBtn;

    private JButton rightBtn;
    private JButton leftBtn;
    private JButton upBtn;
    private JButton downBtn;

    private JLabel stepLabel;
    private JLabel timeLabel;
    private JLabel userLabel;
    private GamePanel gamePanel;

    private User currentUser;
    private String levelName;

    private MusicController musicController;

    ImageIcon downIcon = new ImageIcon("Picture/buttonPic/downBtn.png");
    ImageIcon upIcon = new ImageIcon("Picture/buttonPic/upBtn.png");
    ImageIcon leftIcon = new ImageIcon("Picture/buttonPic/leftBtn.png");
    ImageIcon rightIcon = new ImageIcon("Picture/buttonPic/rightBtn.png");
    ImageIcon exitIcon = new ImageIcon("Picture/buttonPic/exitBtn.png");
    ImageIcon resetIcon = new ImageIcon("Picture/buttonPic/resetBtn.png");
    ImageIcon loadIcon = new ImageIcon("Picture/buttonPic/loadBtn.png");
    ImageIcon saveIcon = new ImageIcon("Picture/buttonPic/saveBtn.png");
    ImageIcon returnIcon = new ImageIcon("Picture/buttonPic/returnBtn.png");
    ImageIcon leaderboardIcon = new ImageIcon("Picture/buttonPic/leaderboardBtn.png");
    ImageIcon downPressedIcon = new ImageIcon("Picture/buttonPic/downPressedBtn.png");
    ImageIcon upPressedIcon = new ImageIcon("Picture/buttonPic/upPressedBtn.png");
    ImageIcon leftPressedIcon = new ImageIcon("Picture/buttonPic/leftPressedBtn.png");
    ImageIcon rightPressedIcon = new ImageIcon("Picture/buttonPic/rightPressedBtn.png");
    ImageIcon exitPressedIcon = new ImageIcon("Picture/buttonPic/exitPressedBtn.png");
    ImageIcon resetPressedIcon = new ImageIcon("Picture/buttonPic/resetPressedBtn.png");
    ImageIcon loadPressedIcon = new ImageIcon("Picture/buttonPic/loadPressedBtn.png");
    ImageIcon savePressedIcon = new ImageIcon("Picture/buttonPic/savePressedBtn.png");
    ImageIcon returnPressedIcon = new ImageIcon("Picture/buttonPic/returnPressedBtn.png");
    ImageIcon gameFrameIcon = new ImageIcon("Picture/framePic/gameFrameSanGuo.png");
    ImageIcon leaderboardPressedIcon = new ImageIcon("Picture/buttonPic/leaderboardPressedBtn.png");

    public GameFrame(int width, int height, MapModel mapModel, LevelFrame levelFrame) {
        this.levelFrame = levelFrame;
        this.levelName = mapModel.getName();
        this.setTitle(mapModel.getName());
        this.setLayout(null);
        this.setSize(width, height);
        JLabel backgroundLabel = new JLabel(gameFrameIcon);
        backgroundLabel.setLayout(null);
        this.setContentPane(backgroundLabel);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(290, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel, this);

        this.restartBtn = FrameUtil.createButton(this, resetIcon,new Point(100, 120), 90, 49);
        restartBtn.setBorderPainted(false);
        restartBtn.setContentAreaFilled(false);
        restartBtn.setFocusPainted(false);
        this.loadBtn = FrameUtil.createButton(this,loadIcon, new Point(80, 180), 138, 46);
        loadBtn.setBorderPainted(false);
        loadBtn.setContentAreaFilled(false);
        loadBtn.setFocusPainted(false);

        this.rightBtn = FrameUtil.createButton(this, rightIcon, new Point(gamePanel.getWidth() + 450, 120), 52, 52);
        rightBtn.setBorderPainted(false);
        rightBtn.setContentAreaFilled(false);
        rightBtn.setFocusPainted(false);
        this.leftBtn = FrameUtil.createButton(this, leftIcon, new Point(gamePanel.getWidth() + 350, 120), 52, 52);
        leftBtn.setBorderPainted(false);
        leftBtn.setContentAreaFilled(false);
        leftBtn.setFocusPainted(false);
        this.upBtn = FrameUtil.createButton(this, upIcon, new Point(gamePanel.getWidth() + 400, 80), 52, 52);
        upBtn.setBorderPainted(false);
        upBtn.setContentAreaFilled(false);
        upBtn.setFocusPainted(false);
        this.downBtn = FrameUtil.createButton(this, downIcon, new Point(gamePanel.getWidth() + 400, 160), 52, 52);
        downBtn.setBorderPainted(false);
        downBtn.setContentAreaFilled(false);
        downBtn.setFocusPainted(false);

        this.stepLabel = FrameUtil.createJLabel(this, "步数：0",
                new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 50), 180, 30);
        this.timeLabel = FrameUtil.createJLabel(this, "时间: 00:00",
                new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 90), 180, 30);
        //this.userLabel = FrameUtil.createJLabel(this, "用户：%",
        //        new Font("serif", Font.ITALIC, 22),
        //        new Point(gamePanel.getWidth() + 80, 10), 180, 30);

        gameTimer = new GameTimer(timeLabel);// 进入游戏时自动开始计时
        controller.setGameTimer(gameTimer);
        gameTimer.start();

        gameTimer.setTimeoutAction(() -> {
            gameTimer.pause();
            MusicController.stopBackgroundMusic();
            MusicController.playLoseSound();
            JOptionPane.showMessageDialog(this,
                    "游戏超时（超过30分钟）！",
                    "游戏结束",
                    JOptionPane.WARNING_MESSAGE);

            returnToLevel();
        });

        gamePanel.setStepLabel(stepLabel);

        restartBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                restartBtn.setIcon(resetPressedIcon);
                restartBtn.setLocation(100, 122);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                restartBtn.setLocation(100, 120);
                restartBtn.setIcon(resetIcon);
                controller.restartGame();
                gameTimer.reset(); // 重置计时器
                gameTimer.start();//重启计时器
                gamePanel.requestFocusInWindow();
            }
        });

        loadBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loadBtn.setIcon(loadPressedIcon);
                loadBtn.setLocation(80, 182);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                loadBtn.setLocation(80, 180);
                loadBtn.setIcon(loadIcon);
                if (currentUser != null) {
                    try {
                        controller.loadGame(currentUser); // 直接加载当前用户的存档
                        JOptionPane.showMessageDialog(gamePanel, "加载成功");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(gamePanel, "加载失败: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "请先登录以加载存档");
                }
                gamePanel.requestFocusInWindow();
            }
        });

        this.saveBtn = FrameUtil.createButton(this, saveIcon,new Point(105, 240), 91, 47);
        saveBtn.setBorderPainted(false);
        saveBtn.setContentAreaFilled(false);
        saveBtn.setFocusPainted(false);

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                saveBtn.setIcon(savePressedIcon);
                saveBtn.setLocation(105, 242);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                saveBtn.setLocation(105, 240);
                saveBtn.setIcon(saveIcon);
                if (currentUser != null) {
                    try {
                        controller.saveGame(currentUser);
                        JOptionPane.showMessageDialog(gamePanel, "保存成功");
                        loadBtn.setEnabled(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(gamePanel, "保存失败");
                    }
                }else {
                    JOptionPane.showMessageDialog(gamePanel, "游客模式无法保存游戏");
                }

                gamePanel.requestFocusInWindow();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                autoSaveGame(); // 窗口关闭时自动保存
            }
        });

        this.exitBtn = FrameUtil.createButton(this,exitIcon, new Point(50, 50), 90, 34);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);

        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                exitBtn.setIcon(exitPressedIcon);
                exitBtn.setLocation(50, 52);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                exitBtn.setLocation(50, 50);
                exitBtn.setIcon(exitIcon);
                MusicController.stopBackgroundMusic();
                MusicController.playBackgroundMusic("Music/BGM/levelFrame.wav");
                returnToLevel();
            }
        });

        this.returnBtn = FrameUtil.createButton(this, returnIcon,new Point(105, 300), 93, 49);
        returnBtn.setBorderPainted(false);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setFocusPainted(false);

        returnBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                returnBtn.setIcon(returnPressedIcon);
                returnBtn.setLocation(105, 302);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                returnBtn.setLocation(105, 300);
                returnBtn.setIcon(returnIcon);
                try {
                    controller.backStep();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gamePanel.requestFocusInWindow();
            }
        });

        rightBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rightBtn.setIcon(rightPressedIcon);
                rightBtn.setLocation(gamePanel.getWidth() + 450, 122);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                rightBtn.setLocation(gamePanel.getWidth() + 450, 120);
                rightBtn.setIcon(rightIcon);
                try {
                    gamePanel.doMoveRight();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gamePanel.requestFocusInWindow();
            }
        });

        leftBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                leftBtn.setIcon(leftPressedIcon);
                leftBtn.setLocation(gamePanel.getWidth() + 350, 122);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                leftBtn.setLocation(gamePanel.getWidth() + 350, 120);
                leftBtn.setIcon(leftIcon);
                try {
                    gamePanel.doMoveLeft();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gamePanel.requestFocusInWindow();
            }
        });

        upBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                upBtn.setIcon(upPressedIcon);
                upBtn.setLocation(gamePanel.getWidth() + 400, 82);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                upBtn.setLocation(gamePanel.getWidth() + 400, 80);
                upBtn.setIcon(upIcon);
                try {
                    gamePanel.doMoveUp();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gamePanel.requestFocusInWindow();
            }
        });

        downBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                downBtn.setIcon(downPressedIcon);
                downBtn.setLocation(gamePanel.getWidth() + 400, 162);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                downBtn.setLocation(gamePanel.getWidth() + 400, 160);
                downBtn.setIcon(downIcon);
                try {
                    gamePanel.doMoveDown();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gamePanel.requestFocusInWindow();
            }
        });

        this.leaderboardBtn = FrameUtil.createButton(this, leaderboardIcon,new Point(550, 300), 154, 50);
        leaderboardBtn.setBorderPainted(false);
        leaderboardBtn.setContentAreaFilled(false);
        leaderboardBtn.setFocusPainted(false);

        leaderboardBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                leaderboardBtn.setIcon(leaderboardPressedIcon);
                leaderboardBtn.setLocation(550, 302);
                MusicController.playClickSound();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                leaderboardBtn.setLocation(550, 300);
                leaderboardBtn.setIcon(leaderboardIcon);
                showLeaderboard();
                gamePanel.requestFocusInWindow();
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> {
            gamePanel.requestFocusInWindow();
        });
    }


    public void setUser(User user) {
        this.currentUser = user;
        saveBtn.setEnabled(user != null); // 控制保存按钮状态
        loadBtn.setEnabled(user != null && user.hasSaveDataForLevel(levelName));
    }

    public User getUser() {
        return currentUser;
    }

    private void autoSaveGame() {
        if (currentUser != null) {
            try {
                controller.saveGame(currentUser);
                System.out.println("自动保存成功");// 调试日志
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "自动保存失败: " + ex.getMessage());
            }
        }
    }

    public void setGamePanel(GamePanel gamePanel) {}

    public GameController getController() {
        return this.controller;
    }

    public void returnToLevel() {
        MusicController.playBackgroundMusic("Music/BGM/levelFrame.wav");
        this.dispose(); // 销毁当前游戏窗口
        levelFrame.setVisible(true); // 显示关卡选择界面
    }

    public String getTime()
    {
        return timeLabel.getText();
    }

    private void showLeaderboard() {
        new RankFrame(levelName).setVisible(true);
    }
}
