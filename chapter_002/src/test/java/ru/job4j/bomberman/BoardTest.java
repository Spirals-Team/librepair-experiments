package ru.job4j.bomberman;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class BoardTest {

    private Board board;

    @Test
    public void createGameBoard() {
        board = new Board(5);
        assertThat(board.size(), is(5));
    }

    @Test
    public void checkPosition() {
        int size = 5;
        board = new Board(size);
        assertFalse(board.checkBoundary(new Point(-1, 0)));
        assertFalse(board.checkBoundary(new Point(0, -1)));
        assertFalse(board.checkBoundary(new Point(-1, -1)));
        assertTrue(board.checkBoundary(new Point(0, 0)));
        assertFalse(board.checkBoundary(new Point(size, size - 1)));
        assertFalse(board.checkBoundary(new Point(size - 1, size)));
        assertFalse(board.checkBoundary(new Point(size, size)));
        assertTrue(board.checkBoundary(new Point(size - 1, size - 1)));
    }
}