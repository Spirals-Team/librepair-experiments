package ru.job4j.controltask.bomberman.board;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Class Cell.
 */
public class Cell extends ReentrantLock {
    /**
     * position on the board by x
     */
    private final int x;
    /**
     * position on the board by y
     */
    private final int y;

    private String name;

    /**
     * Constrcutor.
     * @param x position on the board by x
     * @param y position on the board by y
     */
    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x.
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y.
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method getOwner.
     * @return return owner thread
     */
    @Override
    public Thread getOwner() {
        return super.getOwner();
    }
}
