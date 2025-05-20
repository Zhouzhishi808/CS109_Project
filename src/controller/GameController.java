package controller;

import model.*;
import view.game.BoxComponent;
import view.game.GameFrame;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;
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
    private long animationStartTime;
    private Timer moveTimer;
    private boolean isMoving = false;
    private boolean isUndoing = false;
    private static final int ANIMATION_DURATION = 160;
    private static final int FRAME_INTERVAL = 10;

    private ArrayList<Move> Moves = new ArrayList<>();

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

        int width = getWidth(blockId), height = getHeight(blockId);
        // 根据方块类型确定尺寸

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

        //播放音效
        MusicController.playMoveSound();

        //记录当前地图
        if (!isUndoing) {
            Moves.add(new Move(row, col, direction));
        }

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

    public boolean isMoving() {
        return isMoving;
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
        BoxComponent box = view.getBox(row, col);
        if (box == null || isMoving) return;

        // 计算目标坐标
        int targetX = nextCol * view.getGRID_SIZE() + 2;
        int targetY = nextRow * view.getGRID_SIZE() + 2;
        Point startPos = box.getLocation();

        // 锁定移动状态并记录开始时间
        isMoving = true;
        animationStartTime = System.currentTimeMillis(); // 使用成员变量

        // 初始化计时器
        moveTimer = new Timer(FRAME_INTERVAL, e -> {
            long currentTime = System.currentTimeMillis();
            float progress = (float) (currentTime - animationStartTime) / ANIMATION_DURATION;

            if (progress >= 1.0f) {
                // 动画结束
                box.setLocation(targetX, targetY);
                box.setRow(nextRow);
                box.setCol(nextCol);
                view.repaint();
                view.revalidate();
                moveTimer.stop();
                isMoving = false;
            } else {
                // 线性插值计算中间位置
                int dx = (int) (startPos.x + (targetX - startPos.x) * progress);
                int dy = (int) (startPos.y + (targetY - startPos.y) * progress);
                box.setLocation(dx, dy);
            }
            view.repaint(); // 刷新视图
        });

        moveTimer.start(); // 启动计时器
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
        Moves.clear();
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
        MusicController.stopBackgroundMusic();
        MusicController.playVictorySound();

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

        gameTimer.pause();
        JOptionPane.showMessageDialog(view,
                "恭喜您游戏胜利！\n步数: " + steps + "\n" + gameframe.getTime(),
                "胜利",
                JOptionPane.INFORMATION_MESSAGE);
        MusicController.playBackgroundMusic("Music/BGM/levelFrame.wav");
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

        GameSave save = new GameSave(deepCopy(model.getMatrix()), view.getSteps(), levelName, view.getTimeInSeconds(), this.Moves);
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
            view.refreshBoxes();// 强制刷新视图以确保组件位置同步
            this.Moves = save.getMoves();
            // 加载后重置回退标志位
            isUndoing = false;
            gameTimer.continueFrom(save.getTimeInSeconds());
            view.setTimeInSeconds(save.getTimeInSeconds());
        } catch (FileNotFoundException e) {
            throw new IOException("存档文件不存在");
        }
    }

    public void backStep() throws IOException {
        if (Moves.isEmpty()) {
            JOptionPane.showMessageDialog(view, "无法回退，已无历史步骤", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 标记为回退中
        isUndoing = true;

        // 获取最后一个操作并计算逆向移动参数
        Move lastMove = Moves.removeLast();
        Direction reverseDir = lastMove.getDir().getOpposite();
        int targetRow = lastMove.getRow() + lastMove.getDir().getRow();
        int targetCol = lastMove.getCol() + lastMove.getDir().getCol();

        // 执行逆向移动（会触发动画，但不会记录到 Moves）
        doMove(targetRow, targetCol, reverseDir);

        // 恢复标志位
        isUndoing = false;

        // 更新步数
        view.setSteps(view.getSteps() - 1);
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

    public int getHeight(int id) {
        return switch (id) {
            case 1, 2, 3, 4 -> 1;
            case 5, 6, 7, 8, 9 -> 2;
            default -> 0;
        };
    }

    public int getWidth(int id) {
        return switch (id) {
            case 1, 5, 6, 7, 8 -> 1;
            case 2, 3, 4, 9 -> 2;
            default -> 0;
        };
    }

    public int getType(int id) {
        return switch (id) {
            case 1 -> 1;
            case 2, 3, 4 -> 2;
            case 5, 6, 7, 8 -> 3;
            case 9 -> 4;
            default -> 0;
        };
    }
}
