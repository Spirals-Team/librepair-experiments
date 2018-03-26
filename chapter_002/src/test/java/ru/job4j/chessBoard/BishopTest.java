package ru.job4j.chessBoard;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BishopTest {
    private Board board = new Board();

    @Test
    public void firstTest() {
        boolean result = false;
        Cell dest = new Cell(7, 7);
        Figure bishop = new Bishop(new Cell(0,0));
        board.add(bishop);
        result = board.move(bishop, dest);
        if (result) {
            bishop = bishop.copy(dest);
        }
        assertThat(bishop.position.x, is(7));
        assertThat(bishop.position.y, is(7));
    }

    @Test
    public void secondTest() {
        boolean result = false;
        Cell dest = new Cell(0, 0);
        Figure bishop = new Bishop(new Cell(4,4));
        board.add(bishop);
        result = board.move(bishop, dest);
        if (result) {
            bishop = bishop.copy(dest);
        }
        assertThat(bishop.position.x, is(0));
        assertThat(bishop.position.y, is(0));
    }

    @Test
    public void thirdTest() {
        boolean result = false;
        Cell dest = new Cell(8, 8);
        Figure bishop = new Bishop(new Cell(1,1));
        board.add(bishop);
        result = board.move(bishop, dest);
        if (result) {
            bishop = bishop.copy(dest);
        }
        assertThat(bishop.position.x, is(8));
        assertThat(bishop.position.y, is(8));
    }

    @Test
    public void fourthTest() {
        boolean result = false;
        Cell dest = new Cell(4, 4);
        Figure bishop = new Bishop(new Cell(6,6));
        board.add(bishop);
        result = board.move(bishop, dest);
        if (result) {
            bishop = bishop.copy(dest);
        }
        assertThat(bishop.position.x, is(4));
        assertThat(bishop.position.y, is(4));
    }
}
