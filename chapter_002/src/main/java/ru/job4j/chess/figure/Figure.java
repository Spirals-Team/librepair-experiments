package ru.job4j.chess.figure;

import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public abstract class Figure {
    private final Cell position;
    public Figure(Cell position) {
        this.position = position;
    }
    public boolean exist(Cell source) {
        return this.position.cellEquals(source);
    }
    public boolean exist(Figure figure) {
        return (this.position.getX() == figure.position.getX()
                && this.position.getY() == figure.position.getY()) ? true : false;
    }
    public abstract Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;
    public abstract Figure copy(Cell dest);
}
