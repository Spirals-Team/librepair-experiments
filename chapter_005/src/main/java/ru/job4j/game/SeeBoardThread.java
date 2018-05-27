package ru.job4j.game;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SeeBoardThread implements Runnable {
    private final ReentrantLock[][] board;
    private final BoardAction action = new BoardAction();
    private final int time;

    public SeeBoardThread(ReentrantLock[][] board, int time) {
        this.board = board;
        this.time = time;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {
                    if (this.board[i][j].isLocked()) {
                        if (this.board[i][j].toString().contains("thread main")) {
                            System.out.print("|X");
                        } else if (this.board[i][j].toString().contains("pool-1")) {
                            System.out.print("|M");
                        } else {
                            System.out.print("|h");
                        }
                    } else {
                        System.out.print("| ");
                    }
                }
                System.out.println("|");
                for (int j = 0; j < this.board[i].length; j++) {
                    System.out.print("--");
                }
                System.out.println("-");
            }
            System.out.println();
            action.sleep(this.time);
        }
    }
}

