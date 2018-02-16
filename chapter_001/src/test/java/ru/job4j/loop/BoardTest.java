package ru.job4j.loop;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test method paint.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class BoardTest {
    /**
     * Draws a chessboard 3 x 3.
     */
    @Test
    public void whenPaintBoardWithWidthThreeAndHeightThreeThenStringWithThreeColsAndThreeRows() {
        Board board = new Board();
        String result = board.paint(3, 3);
        final String line = System.getProperty("line.separator");
        String expected = String.format("x x%s x %sx x%s", line, line, line);
        assertThat(result, is(expected));
    }
    /**
     * Draws a chessboard 5 x 4.
     */
    @Test
    public void whenPaintBoardWithWidthFiveAndHeightFourThenStringWithFiveColsAndFourRows() {
        Board board = new Board();
        String result = board.paint(5, 4);
        final String line = System.getProperty("line.separator");
        String expected = String.format("x x x%s x x %sx x x%s x x %s", line, line, line, line);
        assertThat(result, is(expected));
    }
}
