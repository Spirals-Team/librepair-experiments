package ru.job4j.controltask.bomberman.entities;

import ru.job4j.controltask.bomberman.board.Board;
import ru.job4j.controltask.bomberman.board.Cell;
import ru.job4j.controltask.bomberman.move.Step;

public abstract class Entity implements Runnable {
    /**
     * game board
     */
    protected final Board board;
    /**
     * current position on the board
     */
    protected Cell currentCell;
    /**
     * step
     */
    protected final Step step;

    /**
     * Constrcutor.
     * @param board game board
     */
    public Entity(Board board) {
        this.board = board;
        this.step = new Step();
        initEntity();
    }

    /**
     * Method for init entity on the board.
     */
    abstract void initEntity();
}
