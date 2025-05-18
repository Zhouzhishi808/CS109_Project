package view.login;

import controller.GameController;
import model.Constants;
import model.UserManager;
import view.FrameUtil;
import view.game.GameFrame;
import view.level.LevelFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        ImageIcon loginIcon = new ImageIcon("Picture/buttonPic/loginBtn.png");
        ImageIcon registerIcon = new ImageIcon("Picture/buttonPic/registerBtn.png");
        ImageIcon resetIcon = new ImageIcon("Picture/buttonPic/resetBtn.png");
        ImageIcon loginPressedIcon = new ImageIcon("Picture/buttonPic/loginPressedBtn.png");
        ImageIcon registerPressedIcon = new ImageIcon("Picture/buttonPic/registerPressedBtn.png");
        ImageIcon resetPressedIcon = new ImageIcon("Picture/buttonPic/resetPressedBtn.png");
        ImageIcon userIcon = new ImageIcon("Picture/buttonPic/userName.png");
        ImageIcon passwordIcon = new ImageIcon("Picture/buttonPic/password.png");
        ImageIcon loginFrameIcon = new ImageIcon("Picture/framePic/loginFrame.png");

        JLabel backgroundLabel = new JLabel(loginFrameIcon);
        backgroundLabel.setLayout(null);
        this.setContentPane(backgroundLabel);

        // 用户名标签和输入框
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(220, 220), 74, 33,userIcon);
        username = FrameUtil.createJTextField(this, new Point(320, 220), 120, 30);

        // 密码标签和输入框
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(220, 280), 75, 39,passwordIcon);
        password = new JPasswordField();
        password.setLocation(320, 285);
        password.setSize(120, 30);
        this.add(password);

        // 确认和重置按钮
        submitBtn = FrameUtil.createButton(this, loginIcon, new Point(200, 350), 95, 42);
        resetBtn = FrameUtil.createButton(this, resetIcon,new Point(330, 350), 90, 49);
        submitBtn.setBorderPainted(false);
        submitBtn.setContentAreaFilled(false);
        submitBtn.setFocusPainted(false);
        resetBtn.setBorderPainted(false);
        resetBtn.setContentAreaFilled(false);
        resetBtn.setFocusPainted(false);

        submitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                submitBtn.setIcon(loginPressedIcon);
                submitBtn.setLocation(200, 352);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                submitBtn.setLocation(200, 350);
                submitBtn.setIcon(loginIcon);
                String inputUser = username.getText();
                char[] inputPassChars = password.getPassword();
                String inputPass = new String(inputPassChars);

                if (inputUser.isEmpty()) {
                    startAsGuest();
                } else {
                    if (userManager.login(inputUser, inputPass)) {
                        startGameAsUser();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "用户名或密码错误");
                    }
                }
            }
        });

        // 重置按钮监听器
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetBtn.setIcon(resetPressedIcon);
                resetBtn.setLocation(330, 352);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resetBtn.setLocation(330, 350);
                resetBtn.setIcon(resetIcon);
                username.setText("");
                password.setText("");
            }
        });

        // 注册按钮
        JButton registerBtn = FrameUtil.createButton(this, registerIcon, new Point(460, 350), 92, 45);
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setFocusPainted(false);

        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                registerBtn.setIcon(registerPressedIcon);
                registerBtn.setLocation(460, 352);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                registerBtn.setLocation(460, 350);
                registerBtn.setIcon(registerIcon);
                showRegisterDialog();
            }
        });

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