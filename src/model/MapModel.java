package model;

import view.game.GamePanel;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is to record the map of one game. For example:
 */
public class MapModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 8755964951736167768L;
    int[][] matrix;
    String name;

    public MapModel(){}


    public MapModel(int[][] matrix, String name) {
        this.matrix = matrix;
        this.name = name;
    }

    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        return matrix[row][col];
    }

    public String getName() {return this.name;}

    public void setName(String name) {
        this.name = name;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
