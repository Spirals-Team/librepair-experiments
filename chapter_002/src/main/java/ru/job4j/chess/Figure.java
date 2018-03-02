package ru.job4j.chess;

import ru.job4j.chess.exceptions.ImpossibleMoveException;
/**
 * Figure.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public abstract class Figure {
    /*** Cells coordinates.*/
    private final Cell position;
    /**
     * Get position.
     * @return - position
     */
    public Cell getPosition() {
        return this.position;
    }
    /**
     * Constructor.
     * @param position - position
     */
    public Figure(Cell position) {
        this.position = position;
    }
    /**
     * Way of figure.
     * @param dist - cells coordinates
     * @param board - board
     * @return cells array
     * @throws ImpossibleMoveException - if figure not move to coordinate
     */
    public abstract Cell[] way(Cell dist, Board board) throws ImpossibleMoveException;
}
