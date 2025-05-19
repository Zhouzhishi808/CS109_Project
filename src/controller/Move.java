package controller;

import model.Direction;

import java.io.Serial;
import java.io.Serializable;

public class Move implements Serializable {
    @Serial
    private static final long serialVersionUID = 2035090982890175325L;
    int row;
    int col;
    Direction dir;
    public Move(int row, int col, Direction dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }

    public Move() {
    }

    /**
     * 获取
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * 设置
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * 获取
     * @return col
     */
    public int getCol() {
        return col;
    }

    /**
     * 设置
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * 获取
     * @return dir
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * 设置
     * @param dir
     */
    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public String toString() {
        return "Move{row = " + row + ", col = " + col + ", dir = " + dir + "}";
    }
}
