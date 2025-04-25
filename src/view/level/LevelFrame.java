package view.level;
/*
在这个文件中完成关卡选择功能
 */

import model.Constants;

import javax.swing.*;

public class LevelFrame extends JFrame {
    private JButton level1Button;

    public LevelFrame(int width, int height) {
        this.setTitle("选择关卡");
        this.setLayout(null);
        this.setSize(width, height);
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
            for (int col = 0; col < map[row].length; col++) {
                Constants.MAP[row][col] = map[row][col];
            }
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
            for (int col = 0; col < map[row].length; col++) {
                Constants.MAP[row][col] = map[row][col];
            }
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
            for (int col = 0; col < map[row].length; col++) {
                Constants.MAP[row][col] = map[row][col];
            }
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
            for (int col = 0; col < map[row].length; col++) {
                Constants.MAP[row][col] = map[row][col];
            }
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
            for (int col = 0; col < map[row].length; col++) {
                Constants.MAP[row][col] = map[row][col];
            }
        }
    }
}