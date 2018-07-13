package ru.job4j.chess;

/**
 * Базовый класс шахматных фигур
 *
 * @author Alex Rumyantcev
 * @version $Id$
 */
public abstract class Figure {

    // позиция фигуры
    private final Cell position;

    public Figure(final Cell cell) {
        this.position = cell;
    }

    public abstract Cell[] way(Cell source, Cell dest) throws ImposibleMoveException;

    public abstract Figure copy(Cell dest, Board board);

    public abstract boolean move(Cell source, Cell dest, Board board)
            throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException;
}
