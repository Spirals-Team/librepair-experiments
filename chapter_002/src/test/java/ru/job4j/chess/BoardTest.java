package ru.job4j.chess;

import org.junit.Test;

import ru.job4j.chess.exception.ImpossibleMoveException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class BoardTest {
    public Board beoardAllFigureReturn() {
        Board board = new Board();
        Slon slon = new Slon(new Cell(1, 1));
        Slon slon2 = new Slon(new Cell(1, 1));
        Slon slon3 = new Slon(new Cell(2, 2));
        Ladia ladia = new Ladia(new Cell(5, 5));
        board.add(slon);
        board.add(slon2);
        board.add(slon3);
        board.add(ladia);
        return board;
    }

    @Test
    public void testLadiaddmove() throws ImpossibleMoveException {
        Board board = this.beoardAllFigureReturn();
        board.move(new Cell(5, 5), new Cell(5, 8));
        assertThat(board.getFigures()[2].hashCode(), is(new Cell(5, 8).hashCode()));
    }

    @Test
    public void testSlonaddmove() throws ImpossibleMoveException {
        Board board = this.beoardAllFigureReturn();
        if (board.getFigures()[0].equals(new Cell(1, 1))) {
            System.out.println("hf,jnftn");
        }
        board.move(new Cell(2, 2), new Cell(8, 8));
        assertThat(board.getFigures()[1].hashCode(), is(new Cell(8, 8).hashCode()));
    }

}