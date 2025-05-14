package view.level;
/*
在这个文件中完成关卡选择功能
 */

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

        ImageIcon backIcon = new ImageIcon("buttonPic/backBtn.png");
        ImageIcon level1Icon = new ImageIcon("buttonPic/level1Btn.png");
        ImageIcon level2Icon = new ImageIcon("buttonPic/level2Btn.png");
        ImageIcon level3Icon = new ImageIcon("buttonPic/level3Btn.png");
        ImageIcon level4Icon = new ImageIcon("buttonPic/level4Btn.png");
        ImageIcon level5Icon = new ImageIcon("buttonPic/level5Btn.png");

        level1Button = FrameUtil.createButton(this, level1Icon, new Point(133,80), 146,51);
        level1Button.setBorderPainted(false);
        level1Button.setContentAreaFilled(false);
        level1Button.setFocusPainted(false);
        level2Button = FrameUtil.createButton(this, level2Icon, new Point(133,160), 137, 50);
        level2Button.setBorderPainted(false);
        level2Button.setContentAreaFilled(false);
        level2Button.setFocusPainted(false);
        level3Button = FrameUtil.createButton(this, level3Icon, new Point(133,240), 143, 50);
        level3Button.setBorderPainted(false);
        level3Button.setContentAreaFilled(false);
        level3Button.setFocusPainted(false);
        level4Button = FrameUtil.createButton(this, level4Icon, new Point(133,320), 134, 56);
        level4Button.setBorderPainted(false);
        level4Button.setContentAreaFilled(false);
        level4Button.setFocusPainted(false);
        level5Button = FrameUtil.createButton(this, level5Icon, new Point(133,400), 144, 52);
        level5Button.setBorderPainted(false);
        level5Button.setContentAreaFilled(false);
        level5Button.setFocusPainted(false);

        mapModel = new MapModel();


        level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Level1();
                mapModel.setName("横刀立马");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                }
            }
        });
        level2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Level2();
                mapModel.setName("层层设防");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame( 800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                }
            }
        });
        level3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Level3();
                mapModel.setName("四将连关");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                }
            }
        });
        level4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Level4();
                mapModel.setName("水泄不通");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                }
            }
        });
        level5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Level5();
                mapModel.setName("兵分三路");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(800, 500,mapModel, LevelFrame.this);
                LevelFrame.this.setVisible(false);
                gameFrame.setVisible(true);
                if (currentUser != null) {
                    gameFrame.setUser(currentUser);
                }
            }
        });

        this.getContentPane().add(level1Button);
        this.getContentPane().add(level2Button);
        this.getContentPane().add(level3Button);
        this.getContentPane().add(level4Button);
        this.getContentPane().add(level5Button);

        this.returnBtn = FrameUtil.createButton(this, backIcon, new Point(20,40), 90, 34);
        returnBtn.setBorderPainted(false);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setFocusPainted(false);
        returnBtn.addActionListener(e -> returnToLogin());

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    private void Level1() {//横刀立马
        int [][] map = {
                {3,4,4,3},
                {3,4,4,3},
                {3,2,2,3},
                {3,1,1,3},
                {1,0,0,1},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level2() {//层层设防
        int [][] map = {
                {1,4,4,1},
                {1,4,4,1},
                {3,3,2,2},
                {3,3,2,2},
                {0,2,2,0},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level3() {//四将连关
        int [][] map = {
                {4,4,2,2},
                {4,4,2,2},
                {3,3,2,2},
                {3,3,1,1},
                {1,0,0,1},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level4() {//水泄不通
        int [][] map = {
                {3,4,4,1},
                {3,4,4,1},
                {2,2,2,2},
                {2,2,2,2},
                {1,0,0,1},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void Level5() {//兵分三路
        int [][] map = {
                {1,4,4,1},
                {3,4,4,3},
                {3,2,2,3},
                {3,1,1,3},
                {3,0,0,3},
        };
        for (int row = 0; row < map.length; row++) {
            System.arraycopy(map[row], 0, Constants.MAP[row], 0, map[row].length);
        }
    }

    private void returnToLogin() {
        this.dispose(); // 销毁当前游戏窗口
        loginFrame.setVisible(true); // 显示关卡选择界面
    }
}