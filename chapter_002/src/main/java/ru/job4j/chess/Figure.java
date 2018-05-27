package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

abstract class Figure {
    private final Cell position;

    Figure(int x, int y) {
        Cell cell = new Cell(x, y);
        this.position = cell;
    }

    Figure(Cell cell) {
        this.position = cell;
    }

    abstract Cell[] way(Cell source, Cell dest) throws ImposibleMoveException;

    abstract Figure copy(Cell dest);
}
