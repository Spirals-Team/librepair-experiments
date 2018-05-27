package ru.job4j.game;

import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.locks.ReentrantLock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BoardActionTest {
    private BoardAction boardAction = new BoardAction();
    ReentrantLock[][] board = new ReentrantLock[1][1];
    Position position;
    @Before
    public void setup() {
        board[0][0] = new ReentrantLock();
        position = new Position(0, 0);
    }

    @Test
    public void whenTestActionOnBoard() {
        assertThat(board[0][0].isLocked(), is(false));
        boardAction.lockBoard(board, position);
        assertThat(board[0][0].isLocked(), is(true));
        boardAction.unlockBoard(board, position);
        assertThat(board[0][0].isLocked(), is(false));
        assertThat(boardAction.isFreeBoard(board, position), is(true));
    }
}