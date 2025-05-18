package controller;

import model.*;
import view.game.BoxComponent;
import view.game.GameFrame;
import view.game.GamePanel;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private final GameFrame gameframe;
    private GameTimer gameTimer;
    private final int[][] initialMap;
    public static final int CAO_CAO_ID = 4;

    private ArrayList<MapModel> mapModels = new ArrayList<>();

    public GameController(GamePanel view, MapModel model, GameFrame gameframe) {
        this.gameframe = gameframe;
        this.view = view;
        this.model = model;
        this.initialMap = deepCopy(model.getMatrix()); // 保存初始数据
        view.setController(this);

        if (gameTimer != null) {
            gameTimer.setTimeUpdateListener(view::setTimeInSeconds);
        }
    }

    public boolean doMove(int row, int col, Direction direction) throws IOException {
        int blockId = model.getId(row, col);
        if (blockId == 0) return false; // 空位置不可移动

        int width = 1, height = 1;
        // 根据方块类型确定尺寸
        switch (blockId) {
            case 2:  // 1x2普通方块
                width = 2;
                break;
            case 3:  // 2x1关羽方块
                height = 2;
                break;
            case 4:  // 2x2曹操方块
                width = 2;
                height = 2;
                break;
        }

        // 计算移动后的目标区域
        int nextRow = row + direction.getRow();
        int nextCol = col + direction.getCol();

        // 检查边界合法性
        if (!isMoveValid(row, col, nextRow, nextCol, width, height, direction)) {
            return false;
        }

        // 执行移动：清空原位置，填充新位置
        clearOriginalPosition(row, col, width, height);
        fillNewPosition(nextRow, nextCol, blockId, width, height);

        //记录当前地图
        mapModels.add(new MapModel(deepCopy(model.getMatrix()), model.getName()));

        // 更新界面组件位置
        updateBoxComponentPosition(row, col, nextRow, nextCol, blockId, direction);

        //在每次移动后检查是否胜利
        if (checkWin()) {
            showWinDialog();
        }
        return true;
    }

    // 检查移动是否合法
    private boolean isMoveValid(int row, int col, int nextRow, int nextCol,
                                int width, int height, Direction dir) {
        // 检查目标区域是否越界
        if (nextRow < 0 || (nextRow + height) > model.getHeight() ||
                nextCol < 0 || (nextCol + width) > model.getWidth()) {
            return false;
        }

        // 检查目标区域是否全为空
        for (int i = nextRow; i < nextRow + height; i++) {
            for (int j = nextCol; j < nextCol + width; j++) {
                // 跳过当前方块原位置
                if (i >= row && i < row + height && j >= col && j < col + width) {
                    continue;
                }
                if (model.getId(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // 清空原位置
    private void clearOriginalPosition(int row, int col, int width, int height) {
        for (int i = row; i < row + height; i++) {
            for (int j = col; j < col + width; j++) {
                model.getMatrix()[i][j] = 0;
            }
        }
    }

    // 填充新位置
    private void fillNewPosition(int nextRow, int nextCol, int blockId,
                                 int width, int height) {
        for (int i = nextRow; i < nextRow + height; i++) {
            for (int j = nextCol; j < nextCol + width; j++) {
                model.getMatrix()[i][j] = blockId;
            }
        }
    }

    // 更新方块组件位置
    private void updateBoxComponentPosition(int row, int col, int nextRow, int nextCol,
                                            int blockId, Direction dir) {
        BoxComponent box = view.getSelectedBox();
        if (box != null && box.getRow() == row && box.getCol() == col) {
            // 根据方向更新位置（需考虑方块尺寸）
            int deltaX = (nextCol - col) * view.getGRID_SIZE();
            int deltaY = (nextRow - row) * view.getGRID_SIZE();
            box.setLocation(box.getX() + deltaX, box.getY() + deltaY);
            box.setRow(nextRow);
            box.setCol(nextCol);
            box.repaint();
        }
    }

    private int[][] deepCopy(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return copy;
    }

    public void restartGame() {
        // 重置模型数据
        model.setMatrix(deepCopy(initialMap));
        //清楚移动记录
        mapModels.clear();
        // 通知视图重置
        view.resetGame();
    }

    private boolean checkWin() {
        int[][] matrix = model.getMatrix();
        int victoryRowStart = 3;
        for (int i = victoryRowStart; i < victoryRowStart + 2; i++) {
            int victoryColStart = 1;
            for (int j = victoryColStart; j < victoryColStart + 2; j++) {
                if (matrix[i][j] != CAO_CAO_ID) return false;
            }
        }
        return true;
    }

    private void showWinDialog() throws IOException{
        int steps = view.getSteps();

        File rankDir = new File(Constants.RANK_DIRECTORY);
        if (!rankDir.exists()) {
            if (!rankDir.mkdirs()) {
                throw new IOException("无法创建排行榜文件夹");
            }
        }

        if (gameframe.getUser() != null) {
                RankManager.saveRankData(
                    model.getName(),
                    new Rank(
                            gameframe.getUser().getUsername(),
                            gameTimer.getTimeInSeconds()
                    )
            );
        }
        MusicController.playVictorySound();

        gameTimer.pause();
        JOptionPane.showMessageDialog(view,
                "恭喜您游戏胜利！\n步数: " + steps + "\n" + gameframe.getTime(),
                "胜利",
                JOptionPane.INFORMATION_MESSAGE);

        gameframe.returnToLevel(); // 可选：胜利后自动重置游戏
    }

    public void saveGame(User user) throws IOException {
        if (user == null) throw new IOException("用户未登录");

        String levelName = model.getName();

        // 检查并创建存档目录
        File saveDir = new File(Constants.SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            if (!saveDir.mkdirs()) {
                throw new IOException("无法创建存档文件夹");
            }
        }

        GameSave save = new GameSave(deepCopy(model.getMatrix()), view.getSteps(), levelName, view.getTimeInSeconds(), this.mapModels);
        user.setSaveData(save);
        System.out.println(view.getTimeInSeconds());

        // 使用新路径保存文件
        String savePath = Constants.SAVE_DIRECTORY + user.getUsername() + "_" + levelName + ".save";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savePath))) {
            oos.writeObject(save);
        }
    }

    public void loadGame(User user) throws IOException, ClassNotFoundException {
        if (user == null) throw new IllegalArgumentException("用户未登录");

        String levelName = model.getName();

        String savePath = Constants.SAVE_DIRECTORY + user.getUsername() + "_" + levelName +".save";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savePath))) {
            GameSave save = (GameSave) ois.readObject();
            model.setMatrix(deepCopy(save.getMapState()));
            view.resetGame();
            view.setSteps(save.getSteps());
            this.mapModels = save.getMapModels();

            gameTimer.continueFrom(save.getTimeInSeconds());
            view.setTimeInSeconds(save.getTimeInSeconds());
        } catch (FileNotFoundException e) {
            throw new IOException("存档文件不存在");
        }
    }

    public void backStep() {
        if (mapModels.size() <= 1) { // 至少保留初始状态
            JOptionPane.showMessageDialog(view, "无法回退，已无历史步骤", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 移除当前状态，恢复到上一个状态
        mapModels.removeLast(); // 移除当前状态
        MapModel previousModel = mapModels.getLast(); // 获取上一个状态
        model.setMatrix(deepCopy(previousModel.getMatrix()));

        // 更新步数（确保不小于0）
        int currentSteps = view.getSteps();
        view.setSteps(currentSteps - 1);

        // 强制刷新视图（包括清除旧方块和重新生成）
        view.refreshBoxes();
        BoxComponent selectedBox = view.getSelectedBox();
        if (selectedBox != null) {
            selectedBox.setSelected(false);
            view.repaint();
        }
    }

    public void addInitialState() {
        mapModels.add(new MapModel(deepCopy(model.getMatrix()), model.getName()));
    }

    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public void addTimeUpdateListener(){
        gameTimer.addTimeUpdateListener(this.view::setTimeInSeconds);
    }
}
