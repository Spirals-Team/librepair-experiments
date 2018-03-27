package ru.job4j.testtask;

import org.junit.Test;
import ru.job4j.testtask.exceptions.FigureNotFoundException;
import ru.job4j.testtask.exceptions.ImpossibleMoveException;
import ru.job4j.testtask.exceptions.OccupiedWayException;
import ru.job4j.testtask.figures.Elephant;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Test.
 * @author Hincu Andrei (andreih1981@gmail.com) by 12.09.17;
 * @version $Id$
 * @since 0.1
 */
public class BoardTest {
    /**
     * Test putFigure.
     */
    @Test
    public void whenBoardAddNewFigures() {
        Board board = new Board(new Figure[32]);
        board.putFigure(new Elephant("Белый", new Cell(3, 1)));
        board.putFigure(new Elephant("Черный", new Cell(3, 8)));
        Figure[] result = {new Elephant("Белый", new Cell(3, 1)),
                new Elephant("Черный", new Cell(3, 8))};
        Figure[] ex = board.getFigures();
        assertThat(result, is(ex));
    }

    /**
     * Тест что в этой клетке нет фигуры.
     */
    @Test(expected = FigureNotFoundException.class)
    public void whenFigureMoveAndFigureNotFound() {
        Board board = new Board(new Figure[32]);
        board.putFigure(new Elephant("Белый", new Cell(3, 1)));
        board.move(new Cell(3, 2), new Cell(4, 3));
    }

    /**
     * Test правильно ли ходит фигура.
     */
    @Test(expected = ImpossibleMoveException.class)
    public void whenFigureMoveIncorrectThenError() {
        Board board = new Board(new Figure[32]);
        board.putFigure(new Elephant("Белый", new Cell(3, 1)));
        board.move(new Cell(3, 1), new Cell(4, 1));
    }

    /**
     * Test заняты ли клетки на пути фигуры.
     */
    @Test(expected = OccupiedWayException.class)
    public void whenWayOccupiendThenError() {
        Board board = new Board(new Figure[32]);
        board.putFigure(new Elephant("Белый", new Cell(1, 1)));
        board.putFigure(new Elephant("Белый", new Cell(5, 5)));
        board.move(new Cell(1, 1), new Cell(8, 8));
    }

    /**
     * Test when its ok.
     */
    @Test
    public void whenItsOkFigureMove() {
        Board board = new Board(new Figure[32]);
        board.putFigure(new Elephant("Белый", new Cell(1, 1)));
        board.move(new Cell(1, 1), new Cell(8, 8));
        Figure ex = new Elephant("Белый", new Cell(8, 8));
        Figure result = board.getFigures()[0];
        assertThat(result, is(ex));
    }
}
