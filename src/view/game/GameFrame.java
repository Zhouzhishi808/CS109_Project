package view.game;

import controller.GameController;
import model.MapModel;
import model.User;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;

    private User currentUser;

    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle(mapModel.getName());
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "重新开始", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "加载", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "步数：0", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
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
        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void setUser(User user) {
        this.currentUser = user;
        saveBtn.setEnabled(user != null); // 控制保存按钮状态
        loadBtn.setEnabled(user != null && user.hasSaveData());
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

    public GameController getController() {
        return this.controller;
    }
}
