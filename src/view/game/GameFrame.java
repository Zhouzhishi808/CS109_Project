package view.game;

import controller.GameController;
import model.MapModel;
import model.User;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;

    private User currentUser;

    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            gamePanel.requestFocusInWindow();//enable key listener
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
            }
        });
        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void setUser(User user) {
        this.currentUser = user;
        saveBtn.setEnabled(user != null); // 控制保存按钮状态
    }


}
