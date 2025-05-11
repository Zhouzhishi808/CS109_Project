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

    private JLabel stepLabel;
    private JLabel timeLabel;
    private GamePanel gamePanel;

    private User currentUser;
    private String levelName;

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

        this.restartBtn = FrameUtil.createButton(this, "重置", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "加载", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "步数：0",
                new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 50), 180, 30);
        this.timeLabel = FrameUtil.createJLabel(this, "时间: 00:00",
                new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 90), 180, 30);

        gameTimer = new GameTimer(timeLabel);// 进入游戏时自动开始计时
        controller.setGameTimer(gameTimer);
        gameTimer.start();

        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gameTimer.reset(); // 重置计时器
            gameTimer.start();
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

        this.saveBtn = FrameUtil.createButton(this, "保存", new Point(gamePanel.getWidth() + 80, 300), 80, 50);
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

        this.exitBtn = FrameUtil.createButton(this, "返回关卡",
                new Point(gamePanel.getWidth() + 80, 390), 100, 30);
        exitBtn.addActionListener(e -> returnToLevel());

        this.returnBtn = FrameUtil.createButton(this, "退", new Point(50, 50), 50, 50);
        returnBtn.addActionListener(e -> {
            controller.backStep();
            gamePanel.requestFocusInWindow();
        });
        //todo: add other button here
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
}
