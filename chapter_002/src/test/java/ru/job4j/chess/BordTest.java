package ru.job4j.chess;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BordTest {
    /**
     * @version $Id$
     * @since 0.1
     */
    @Test
    public void methodBordTest() {
        Board board = new Board();
        Cell source = new Cell(6, 1);
        Cell dest = new Cell(1, 6);
        Figure bishop = new Bishop(source);
        board.add(bishop);
        assertThat(board.move(source, dest), is(true));
    }
}
