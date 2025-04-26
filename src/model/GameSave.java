package model;

import java.io.Serializable;

public class GameSave implements Serializable {
    private final int[][] mapState;
    private final int steps;
    private final String levelName; // 关卡名称字段

    public GameSave(int[][] mapState, int steps, String levelName) {
        this.mapState = mapState;
        this.steps = steps;
        this.levelName = levelName;
    }

    // Getters
    public int[][] getMapState() { return mapState; }
    public int getSteps() { return steps; }
    public String getLevelName() { return levelName; }
}