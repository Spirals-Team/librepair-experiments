package ru.job4j.chess;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ChessTest {

    // Общая доска
    Board board;

    /**
     * Процедура для инициализации доски перед тестом
     */
    @Before
    public void beforeInit() {
        Figure[][] figures = new Figure[8][8];
        board = new Board(figures);
    }

    /**
     * Тест создания и копирования слона, с проверкой наличия 2х слонов на доске
     */
    @Test
    public void whenCreateAndCopyBishopsThenShowTwoBishopsOnBoard() {
        new Bishop(new Cell(4, 5), this.board).copy(new Cell(6, 4), this.board);
        assertThat(board.showFigure(new Cell(4, 5)) != null && board.showFigure(new Cell(6, 4)) != null,
                is(true));
    }

    /**
     * Тест успешного перемещения слона из одной клетки в другую
     *
     * @throws ImposibleMoveException  путь для данной фигуры невозможен
     * @throws OccupiedWayException    путь занят другой фигурой
     * @throws FigureNotFoundException нет фигур в исходной позиции
     */
    @Test
    public void whenMoveBishopThenShowBishopInNewLoc()
            throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Cell source = new Cell(6, 7);
        Cell dest = new Cell(3, 4);
        Bishop bish = new Bishop(source, this.board);
        boolean move = bish.move(source, dest, this.board);
        assertThat(board.showFigure(source) == null && board.showFigure(dest) != null && move,
                is(true));
    }

    /**
     * Тест отсутствия слона в исходной позиции, при перемещении слона из одной клетки в другую
     *
     * @throws ImposibleMoveException  путь для данной фигуры невозможен
     * @throws OccupiedWayException    путь занят другой фигурой
     * @throws FigureNotFoundException нет фигур в исходной позиции
     */
    @Test(expected = FigureNotFoundException.class)
    public void whenMoveBishopThenFigureNotFoundException()
            throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Cell source = new Cell(6, 7);
        Cell dest = new Cell(3, 4);
        Bishop bish = new Bishop(new Cell(6, 6), this.board);
        boolean move = bish.move(source, dest, this.board);
    }

    /**
     * Тест нахождения другой фигуры на пути перемещения слона из одной клетки в другую
     *
     * @throws ImposibleMoveException  путь для данной фигуры невозможен
     * @throws OccupiedWayException    путь занят другой фигурой
     * @throws FigureNotFoundException нет фигур в исходной позиции
     */
    @Test(expected = OccupiedWayException.class)
    public void whenMoveBishopThenOccupiedWayException()
            throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Cell source = new Cell(6, 7);
        Cell dest = new Cell(3, 4);
        Bishop bish = new Bishop(source, this.board);
        new Bishop(new Cell(4, 5), this.board);
        boolean move = bish.move(source, dest, this.board);
    }

    /**
     * Тест невозможности перемещения слона из одной клетки в другую
     *
     * @throws ImposibleMoveException  путь для данной фигуры невозможен
     * @throws OccupiedWayException    путь занят другой фигурой
     * @throws FigureNotFoundException нет фигур в исходной позиции
     */
    @Test(expected = ImposibleMoveException.class)
    public void whenMoveBishopThenImposibleMoveException()
            throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Cell source = new Cell(6, 7);
        Cell dest = new Cell(1, 4);
        Bishop bish = new Bishop(source, this.board);
        boolean move = bish.move(source, dest, this.board);
    }
}
