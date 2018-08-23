package ru.job4j.controltask.bomberman.board;

import java.util.Random;


/**
 * Class Board.
 */
public class Board {
    /**
     * @param board game board
     */
    private final Cell[][] board;
    /**
     * @param maxX max value by X for our board
     */
    private final int maxX;
    /**
     * @param MaxY max value by Y for our board
     */
    private final int maxY;
    /**
     * @param minX min value by X for our board
     */
    private final int minX = 0;
    /**
     * @param minY min value by Y for our board
     */
    private final int minY = 0;
    /**
     * @param finish finish cell
     */
    private final Cell finish;
    /**
     * @param isFinish status by game
     */
    private volatile boolean isFinish = false;


    /**
     * Constrcutor.
     * @param x count of rows
     * @param y count of column
     */
    public Board(int x, int y) {
        this.board = new Cell[x][y];
        maxX = board.length;
        maxY = board[0].length;
        finish = initBoard();
    }

    /**
     * Method for initializing a whiteboard.
     * @return finish cell
     */
    private Cell initBoard() {
        initRoad();
        initWall();
        return initFinish();
    }

    /**
     * Method for for initializing a road-cells.
     */
    private void initRoad() {
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                this.board[i][j] = new Cell(i, j);
                this.board[i][j].setName("Road");
            }
        }
    }

    /**
     * Method for for initializing a wall-cells.
     */
    private void initWall() {
        for (int i = 0; i < maxX; i = i + 2) {
            for (int j = 1; j < maxY; j = j + 2) {
                this.board[i][j].lock();
                this.board[i][j].setName("Wall");
            }
        }
    }

    /**
     * Method for for initializing a finish cells.
     * @return finish cell
     */
    private Cell initFinish() {
        int x = this.maxX / 2;
        int y = this.maxY / 2;
        Cell temp;
        while (true) {
            temp = this.board[x + new Random().nextInt(getMaxX() - x)][y + new Random().nextInt(getMaxY() - y)];
            if (!temp.isLocked()) {
                temp.setName("Finish");
                return temp;
            }
        }
    }

    /**
     * Getter for board.
     * @return board
     */
    public Cell[][] getBoard() {
        return board;
    }

    /**
     * Getter for MaxX.
     * @return MaxX
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Getter for MaxY.
     * @return MaxY
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Getter for MinX.
     * @return MinX
     */
    public int getMinX() {
        return minX;
    }

    /**
     * Getter for MinY.
     * @return minY
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Getter for finish cell
     * @return finish
     */
    public Cell getFinish() {
        return finish;
    }

    /**
     * Getter for check a finish status.
     * @return game statuc
     */
    public boolean isFinish() {
        return isFinish;
    }

    /**
     * Setter for check a finish status.
     * @param finish set finish statuc
     */
    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
