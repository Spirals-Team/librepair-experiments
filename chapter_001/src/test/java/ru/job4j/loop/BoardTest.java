package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoardTest {
    @Test
    public void whenPaintBoardWithWidthThreeAndHeightThreeThenStringWithThreeColsAndThreeRows() {
        Board board = new Board();
        String result = board.paint(3, 3);
        String expected = String.format("x x\\n x \\nx x\\n");
        assertThat(result, is(expected));
    }

    @Test
    public void whenPaintBoardWithWidthFiveAndHeightFourThenStringWithFiveColsAndFourRows() {
        Board board = new Board();
        String result = board.paint(5, 4);
        String expected = String.format("x x x\\n x x \\nx x x\\n x x \\n");
        assertThat(result, is(expected));
    }

    @Test
    public void whenPaintBoardWithWidthFourAndHeightFourThenStringWithFourColsAndFourRows() {
        Board board = new Board();
        String result = board.paint(4, 4);
        String expected = String.format("x x \\n x x\\nx x \\n x x\\n");
        assertThat(result, is(expected));
    }
}
