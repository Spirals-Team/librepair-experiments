package ru.job4j.chess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BishopTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    private final int startX = 2;
    private final int startY = 7;
    private final Cell startCell = new Cell(startX, startY);

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    @Test
    public void whenNotCorrectMove() {
        Bishop bishop = new Bishop(startCell);
        try {
            bishop.way(startCell, new Cell(3, 1));
        } catch (ImposibleMoveException ime) {
            System.out.println(ime.getMessage());
        }
        assertThat(this.mem.toString(), is(String.format("This figure does not move this...%n")));
    }

    @Test
    public void whenNotCorrectMoveOutOfBoard() {
        Bishop bishop = new Bishop(startCell);
        try {
            bishop.way(startCell, new Cell(3, 8));
        } catch (ImposibleMoveException ime) {
            System.out.println(ime.getMessage());
        }
        assertThat(this.mem.toString(), is(String.format("Move out of board...%n")));
    }

    @Test
    public void whenNotCorrectDiagonal() {
        Bishop bishop = new Bishop(startCell);
        try {
            bishop.way(startCell, new Cell(7, 4));
        } catch (ImposibleMoveException ime) {
            System.out.println(ime.getMessage());
        }
        assertThat(this.mem.toString(), is(String.format("Not correct move...%n")));
    }

    @Test
    public void whenCorrectMove() {
        Bishop bishop = new Bishop(startCell);
        Cell[] mass = bishop.way(startCell, new Cell(6, 3));
        Cell[] expect = {new Cell(3, 6),
                new Cell(4, 5),
                new Cell(5, 4),
                new Cell(6, 3),
                null,
                null,
                null};
        assertThat(mass, is(expect));
    }
}