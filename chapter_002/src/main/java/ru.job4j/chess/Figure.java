package ru.job4j.chess;

import ru.job4j.chess.exception.ImpossibleMoveException;

public abstract class Figure {
    private final Cell position;

    public Figure(Cell position) {
        this.position = position;
    }

    public abstract Cell[] way(Cell dist) throws ImpossibleMoveException;
}
