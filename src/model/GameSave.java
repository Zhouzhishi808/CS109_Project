// 文件: GameSave.java
package model;

import java.io.Serializable;

public class GameSave implements Serializable {
    private final int[][] mapState;
    private final int steps;

    public GameSave(int[][] mapState, int steps) {
        this.mapState = mapState;
        this.steps = steps;
    }

    public int[][] getMapState() { return mapState; }
    public int getSteps() { return steps; }
}