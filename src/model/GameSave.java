package model;

import java.io.Serial;
import java.io.Serializable;

public class GameSave implements Serializable {
    @Serial
        private static final long serialVersionUID = -6347149812494695620L;
    private final int[][] mapState;
    private final int steps;
    private final String levelName;
    private final int timeInSeconds;

    public GameSave(int[][] mapState, int steps, String levelName, int timeInSeconds) {
        this.mapState = mapState;
        this.steps = steps;
        this.levelName = levelName;
        this.timeInSeconds = timeInSeconds;
    }

    // Getters
    public int[][] getMapState() { return mapState; }
    public int getSteps() { return steps; }
    public String getLevelName() { return levelName; }
    public int getTimeInSeconds() { return timeInSeconds; }
}