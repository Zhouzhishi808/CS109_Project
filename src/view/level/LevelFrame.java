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

    private final JButton level1Button = new JButton("横刀立马");
    private final JButton level2Button = new JButton("层层设防");
    private final JButton level3Button = new JButton("四将连关");
    private final JButton level4Button = new JButton("水泄不通");
    private final JButton level5Button = new JButton("兵分三路");
    private final JButton returnBtn;

    private User currentUser;
    private MapModel mapModel;

    public LevelFrame(int width, int height, LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.setTitle("选择关卡");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        level1Button.setBounds(148, 80, 100, 40);
        level2Button.setBounds(148, 160, 100, 40);
        level3Button.setBounds(148, 240, 100, 40);
        level4Button.setBounds(148, 320, 100, 40);
        level5Button.setBounds(148, 400, 100, 40);

        mapModel = new MapModel();


        level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Level1();
                mapModel.setName("横刀立马");
                mapModel.setMatrix(Constants.MAP);
                gameFrame = new GameFrame(450, 450,mapModel, LevelFrame.this);
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
                gameFrame = new GameFrame(450, 450,mapModel, LevelFrame.this);
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
                gameFrame = new GameFrame(450, 450,mapModel, LevelFrame.this);
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
                gameFrame = new GameFrame(450, 450,mapModel, LevelFrame.this);
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
                gameFrame = new GameFrame(450, 450,mapModel, LevelFrame.this);
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

        this.returnBtn = FrameUtil.createButton(this, "返回",
                new Point(20,40), 100, 30);
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
                {3,0,0,3},
                {3,0,0,3},
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