package ru.job4j.game;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class HeroThread implements Runnable {
    private Position position;
    private final ReentrantLock[][] board;
    private final Motion motion = new Motion();
    private final BoardAction action = new BoardAction();
    private final int time;

    HeroThread(ReentrantLock[][] board, Position position, int time) {
        this.position = position;
        this.board = board;
        this.time = time;
    }

    @Override
    public void run() {
        action.lockBoard(this.board, this.position);
        while (!Thread.interrupted()) {
            Position newPosition = motion.getWay(this.board, this.position);
            if (!capture(newPosition, this.time)) {
                newPosition = motion.getWay(this.board, this.position, newPosition);
                capture(newPosition, this.time);
            } else {
                action.sleep(this.time);
            }
        }

    }

    private boolean capture(Position newPosition, int time) {
        long timeStart = System.currentTimeMillis();
        long timeStop;
        boolean capture = false;
        do {
            timeStop = System.currentTimeMillis();
            if (!capture && action.tryLockBoard(board, newPosition)) {
                action.unlockBoard(board, position);
                position = newPosition;
                capture = true;
            }
        } while (timeStop - timeStart < time);
        return capture;
    }
}
