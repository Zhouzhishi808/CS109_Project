package view.login;

import controller.GameController;
import model.UserManager;
import view.FrameUtil;
import view.game.GameFrame;
import view.level.LevelFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private JTextField username;
    private JPasswordField password; // 修正为 JPasswordField
    private JButton submitBtn;
    private JButton resetBtn;
    private LevelFrame levelFrame;
    private final UserManager userManager = new UserManager();

    public LoginFrame(int width, int height) {
        this.setTitle("登录界面");
        this.setLayout(null);
        this.setSize(width, height);

        // 用户名标签和输入框
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "用户名:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);

        // 密码标签和输入框（使用 JPasswordField）
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "密码:");
        password = new JPasswordField(); // 手动初始化
        password.setLocation(120, 80);
        password.setSize(120, 40);
        this.add(password);

        // 确认和重置按钮
        submitBtn = FrameUtil.createButton(this, "登录", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "重置", new Point(160, 140), 100, 40);

        // 提交按钮监听器
        submitBtn.addActionListener(e -> {
            String inputUser = username.getText();
            char[] inputPassChars = password.getPassword(); // 正确获取密码
            String inputPass = new String(inputPassChars);

            if (inputUser.isEmpty()) {
                startAsGuest();
            } else {
                if (userManager.login(inputUser, inputPass)) {
                    startGameAsUser();
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误");
                }
            }
        });

        // 重置按钮监听器
        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        // 注册按钮
        JButton registerBtn = FrameUtil.createButton(this, "注册", new Point(100, 180), 80, 30);
        registerBtn.addActionListener(e -> showRegisterDialog());

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void showRegisterDialog() {
        JTextField regUser = new JTextField();
        JPasswordField regPass = new JPasswordField(); // 使用 JPasswordField
        Object[] message = {
                "用户名:", regUser,
                "密码:", regPass
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "注册",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String username = regUser.getText();
            char[] passwordChars = regPass.getPassword();
            String password = new String(passwordChars);

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名和密码不能为空");
                return;
            }

            if (userManager.register(username, password)) {
                JOptionPane.showMessageDialog(this, "注册成功");
            } else {
                JOptionPane.showMessageDialog(this, "用户名已存在");
            }
        }
    }

    private void startAsGuest() {
        this.levelFrame.setUser(null);
        this.levelFrame.setVisible(true);
        this.setVisible(false);
    }

    private void startGameAsUser() {
        this.levelFrame.setUser(userManager.getCurrentUser());
        this.levelFrame.setVisible(true);
        this.setVisible(false);
    }

    public void setLevelFrame(LevelFrame levelFrame) {
        this.levelFrame = levelFrame;
    }
}