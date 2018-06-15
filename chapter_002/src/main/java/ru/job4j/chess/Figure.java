package ru.job4j.chess;

public abstract class Figure {
    final Cell position;

    public  Figure(Cell position) {
        this.position = position;
    }

    public abstract Cell[] way(Cell source, Cell dest)
            throws  ImposableMoveException;

    public abstract Figure copy(Cell dest);

}