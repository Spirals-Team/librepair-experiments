package ru.job4j.chess;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;


public class BoardTest {


    @Test
    public void moveWhenWay() {
     Board board = new Board();
     Cell source = new Cell(3, 2);
     Cell dest = new Cell(6, 5);
     board.add(new Bishop(source));
     assertThat(board.move(source, dest), is(true));
    }
}