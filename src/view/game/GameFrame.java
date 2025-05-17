package view.game;

import controller.GameController;
import model.GameTimer;
import model.MapModel;
import model.User;
import view.FrameUtil;
import view.level.LevelFrame;

import javax.swing.*;
import java.awt.*;
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
    private GamePanel gamePanel;

    private User currentUser;
    private String levelName;

    ImageIcon downIcon = new ImageIcon("Picture/buttonPic/downBtn.png");
    ImageIcon upIcon = new ImageIcon("Picture/buttonPic/upBtn.png");
    ImageIcon leftIcon = new ImageIcon("Picture/buttonPic/leftBtn.png");
    ImageIcon rightIcon = new ImageIcon("Picture/buttonPic/rightBtn.png");
    ImageIcon exitIcon = new ImageIcon("Picture/buttonPic/exitBtn.png");
    ImageIcon resetIcon = new ImageIcon("Picture/buttonPic/resetBtn.png");
    ImageIcon loadIcon = new ImageIcon("Picture/buttonPic/loadBtn.png");
    ImageIcon saveIcon = new ImageIcon("Picture/buttonPic/saveBtn.png");
    ImageIcon returnIcon = new ImageIcon("Picture/buttonPic/returnBtn.png");

    public GameFrame(int width, int height, MapModel mapModel, LevelFrame levelFrame) {
        this.levelFrame = levelFrame;
        this.levelName = mapModel.getName();
        this.setTitle(mapModel.getName());
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel, this);

        controller.addInitialState();

        this.restartBtn = FrameUtil.createButton(this, resetIcon,new Point(gamePanel.getWidth() + 80, 120), 90, 49);
        restartBtn.setBorderPainted(false);
        restartBtn.setContentAreaFilled(false);
        restartBtn.setFocusPainted(false);
        this.loadBtn = FrameUtil.createButton(this,loadIcon, new Point(gamePanel.getWidth() + 60, 210), 138, 46);
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

        gameTimer = new GameTimer(timeLabel);// 进入游戏时自动开始计时
        controller.setGameTimer(gameTimer);
        gameTimer.start();

        gameTimer.setTimeoutAction(() -> {
            gameTimer.pause();
            JOptionPane.showMessageDialog(this,
                    "游戏超时（超过30分钟）！",
                    "游戏结束",
                    JOptionPane.WARNING_MESSAGE);
            returnToLevel();
        });

        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gameTimer.reset(); // 重置计时器
            gameTimer.start();//重启计时器
            gamePanel.requestFocusInWindow();
        });
        this.loadBtn.addActionListener(e -> {
            if (currentUser != null) {
                try {
                    controller.loadGame(currentUser); // 直接加载当前用户的存档
                    JOptionPane.showMessageDialog(this, "加载成功");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "加载失败: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先登录以加载存档");
            }
            gamePanel.requestFocusInWindow();
        });

        this.saveBtn = FrameUtil.createButton(this, saveIcon,new Point(gamePanel.getWidth() + 80, 300), 91, 47);
        saveBtn.setBorderPainted(false);
        saveBtn.setContentAreaFilled(false);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            if (currentUser != null) {
                try {
                    controller.saveGame(currentUser);
                    JOptionPane.showMessageDialog(this, "保存成功");
                    loadBtn.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "保存失败");
                }
            }else {
                JOptionPane.showMessageDialog(this, "游客模式无法保存游戏");
            }

            gamePanel.requestFocusInWindow();
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
        exitBtn.addActionListener(e -> returnToLevel());

        this.returnBtn = FrameUtil.createButton(this, returnIcon,new Point(gamePanel.getWidth() + 80, 390), 93, 49);
        returnBtn.setBorderPainted(false);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setFocusPainted(false);
        returnBtn.addActionListener(e -> {
            controller.backStep();
            gamePanel.requestFocusInWindow();
        });

        this.rightBtn.addActionListener(e -> {
            try {
                gamePanel.doMoveRight();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });
        this.leftBtn.addActionListener(e -> {
            try {
                gamePanel.doMoveLeft();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });
        this.upBtn.addActionListener(e -> {
            try {
                gamePanel.doMoveUp();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });
        this.downBtn.addActionListener(e -> {
            try {
                gamePanel.doMoveDown();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            gamePanel.requestFocusInWindow();
        });

        this.leaderboardBtn = FrameUtil.createButton(this, downIcon,new Point(400, 400), 50, 50);
        leaderboardBtn.addActionListener(e -> {
            showLeaderboard();
            gamePanel.requestFocusInWindow();
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
