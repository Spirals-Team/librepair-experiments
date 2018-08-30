package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static java.lang.Thread.currentThread;
import static ru.job4j.bomberman.GeneratorUtils.randomPoint;

/**
 * Game board.
 */
public class Board {

    private final static Logger LOG = Logger.getLogger(Board.class.getName());

    private final ReentrantLock[][] gameBoard;

    public Board(final int size) {
        this.gameBoard = new ReentrantLock[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.gameBoard[i][j] = new ReentrantLock();
            }
        }
    }

    /**
     * Arrange rocks on game board
     *
     * @param number quantity
     */
    public void arrangeRocks(final int number) {
        for (int i = 0; i < number; i++) {
            setIfPossible(randomPoint(0, size()));
        }
    }

    /**
     * Get game board
     *
     * @return game board
     */
    public ReentrantLock[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Get size of game board
     *
     * @return size
     */
    public int size() {
        return gameBoard.length;
    }

    /**
     * Lock game board cell
     *
     * @param pos coordinates of cell to lock
     * @return {@code true} if lock successfully
     */
    public boolean setIfPossible(final Point pos) {
        boolean result = false;
        if (checkBoundary(pos)) {
            try {
                LOG.info(String.format("%s tries to lock [%d][%d]",
                    currentThread().getName(), pos.getPosX(), pos.getPosY()));
                result = gameBoard[pos.getPosX()][pos.getPosY()].tryLock(
                    500L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Unlock game board cell
     *
     * @param pos coordinates of cell to unlock
     */
    public void unlockPosition(final Point pos) {
        LOG.info(String.format("%s tries to unlock [%d][%d]",
            currentThread().getName(), pos.getPosX(), pos.getPosY()));
        gameBoard[pos.getPosX()][pos.getPosY()].unlock();
    }

    /**
     * Check that point located inside game board
     *
     * @param point point
     * @return {@code true} if point located inside game board
     */
    public boolean checkBoundary(final Point point) {
        int x = point.getPosX();
        int lengthX = gameBoard.length;
        boolean result = false;
        if (checkPos(x, lengthX)) {
            int y = point.getPosY();
            int lengthY = gameBoard[x].length;
            if (checkPos(y, lengthY)) {
                result = true;
            }
        }
        return result;
    }

    private boolean checkPos(final int z, final int max) {
        return z >= 0 && z < max;
    }
}
