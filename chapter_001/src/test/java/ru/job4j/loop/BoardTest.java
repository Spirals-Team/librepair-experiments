package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BoardTest {

    /**
     * Тест доски 3 на 3.
     */
    @Test
    public void whenPaintBoardWithWidthAndHeightThreeThenStringWithThreeColsAndRows() {
        Board board = new Board();
        String rsl = board.paint(3, 3);
        final String ln = System.getProperty("line.separator");
        assertThat(rsl, is(
                String.format("X X%s X %sX X%s", ln, ln, ln)
                )
        );
    }

    /**
     * Тест доски 5 на 4.
     */
    @Test
    public void whenPaintBoardWithWidthFiveAndHeightFourThenStringWithFiveColsAndFourRows() {
        //напишите здесь тест, проверяющий формирование доски 5 на 4.
        Board board = new Board();
        String rsl = board.paint(5, 4);
        final String ln = System.getProperty("line.separator");
        assertThat(rsl, is(
                String.format("X X X%s X X %sX X X%s X X %s", ln, ln, ln, ln)
                )
        );
    }
}