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
        JFrame confirmFrame = new JFrame("游客登陆");
        confirmFrame.setVisible(false);
        confirmFrame.setLocationRelativeTo(null);
        confirmFrame.setAlwaysOnTop(true);
        confirmFrame.setSize(280,280);

        ImageIcon loginIcon = new ImageIcon("buttonPic/loginBtn.png");
        ImageIcon registerIcon = new ImageIcon("buttonPic/registerBtn.png");
        ImageIcon resetIcon = new ImageIcon("buttonPic/resetBtn.png");

        // 用户名标签和输入框
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(500, 120), 70, 30, "用户名:");
        username = FrameUtil.createJTextField(this, new Point(570, 120), 120, 30);

        // 密码标签和输入框
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(500, 180), 70, 30, "密码:");
        password = new JPasswordField();
        password.setLocation(570, 180);
        password.setSize(120, 30);
        this.add(password);

        // 确认和重置按钮
        submitBtn = FrameUtil.createButton(this, loginIcon, new Point(480, 250), 95, 42);
        resetBtn = FrameUtil.createButton(this, resetIcon,new Point(680, 250), 90, 49);
        submitBtn.setBorderPainted(false);
        submitBtn.setContentAreaFilled(false);
        submitBtn.setFocusPainted(false);
        resetBtn.setBorderPainted(false);
        resetBtn.setContentAreaFilled(false);
        resetBtn.setFocusPainted(false);

        submitBtn.addActionListener(e -> {
            String inputUser = username.getText();
            char[] inputPassChars = password.getPassword();
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
        JButton registerBtn = FrameUtil.createButton(this, registerIcon, new Point(580, 250), 92, 45);
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setFocusPainted(false);
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