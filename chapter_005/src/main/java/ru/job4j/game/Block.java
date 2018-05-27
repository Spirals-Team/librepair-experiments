package ru.job4j.game;

import java.util.concurrent.locks.ReentrantLock;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * класс установки блоков.
 */
public class Block {
    public Block(ReentrantLock[][] board, Difficulty.Dif dif) {
        int value = 0;
        int size = board.length * board[0].length;
        if (dif.equals(Difficulty.Dif.EASY)) {
            value = (int) (size * 0.15f);
        } else if (dif.equals(Difficulty.Dif.NORMAL)) {
            value = (int) (size * 0.2f);
        } else if (dif.equals(Difficulty.Dif.HARD)) {
            value = (int) (size * 0.25f);
        }
        createBlocks(board, value);
    }

    private void createBlocks(ReentrantLock[][] board, int value) {
        BoardAction action = new BoardAction();
        for (int i = 0; i < value; i++) {
            action.lockBoard(board, action.generateRandomFreePosition(board));
        }
    }
}
