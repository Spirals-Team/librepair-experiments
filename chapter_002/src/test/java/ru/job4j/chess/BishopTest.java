package ru.job4j.chess;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;


public class BishopTest {

    @Test
    public void whenBishopWayToCell() {
        Cell c1 = new Cell(2, 1);
        Cell c2 = new Cell(5, 4);
        Cell c3 = new Cell(3, 2);
        Cell c4 = new Cell(4, 3);

        Figure bishop = new Bishop(c1);
        Cell[] expectArray = new Cell[] {c3, c4, c2};
        Cell[] actualArray = bishop.way(c1, c2);
        assertThat(actualArray, is(expectArray));
    }
    @Test(expected = ImposableMoveException.class)
    public void whenBishopWayWhenExceptionMovement() {
        Cell c1 = new Cell(2, 1);
        Cell c2 = new Cell(7, 4);
        Cell c3 = new Cell(3, 2);
        Cell c4 = new Cell(4, 3);

        Figure bishop = new Bishop(c1);
        System.out.print(c1.hashCode());
        Cell[] expectArray = new Cell[] {c3, c4, c2};
        Cell[] actualArray = bishop.way(c1, c2);
        assertThat(actualArray, is(expectArray));
    }
    @Test
    public void whenFigureCopyNewDestThanFigureIsNewBishopFromDest() {
        Cell c1 = new Cell(4, 3);
        Cell c2 = new Cell(6, 4);
        Bishop bishop = new Bishop(c1);
        Bishop next = new Bishop(c2);
        assertThat(bishop.copy(c2).position, is(next.position));
    }
}