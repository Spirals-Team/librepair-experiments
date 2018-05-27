package ru.job4j.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BomberMan {
    private final ReentrantLock[][] board;
    private final ExecutorService service;
    private final int size;
    private static boolean endGame = false;

    public BomberMan(int height, int width, Difficulty.Dif difBlock, Difficulty.Dif difMonstrs) {
        board = new ReentrantLock[height][width];
        size = getDif(difMonstrs);
        service = Executors.newFixedThreadPool(size);
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
        new Block(this.board, difBlock);
    }

    private int getDif(Difficulty.Dif difMonstrs) {
        int value = 0;
        if (difMonstrs.equals(Difficulty.Dif.EASY)) {
            value = 1;
        } else if (difMonstrs.equals(Difficulty.Dif.NORMAL)) {
            value = 3;
        } else if (difMonstrs.equals(Difficulty.Dif.HARD)) {
            value = 6;
        }
        return value;
    }

    public void start() throws InterruptedException {
        BoardAction action = new BoardAction();
        Thread hero = new Thread(new HeroThread(this.board, action.generateRandomFreePosition(this.board), 500));
        Thread see = new Thread(new SeeBoardThread(this.board, 1000));
        hero.start();
        for (int i = 0; i < size; i++) {
            service.submit(new Thread(new MonsterThread(
                    this.board, action.generateRandomFreePosition(this.board), 500)));
        }
        see.start();
        while (!endGame) {
            Thread.sleep(1000);
        }
        service.shutdownNow();
        see.interrupt();
        hero.interrupt();
        System.out.println("The End.");
    }

    public static void stop() {
        endGame = true;
    }
}
