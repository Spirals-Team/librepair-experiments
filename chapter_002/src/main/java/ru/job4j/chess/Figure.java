package ru.job4j.chess;

/**
 * Этот класс будет описывать абстрактное поведение шахматной доски
 */

public abstract class Figure {
    final Cell position;

    Figure(Cell position) {
        this.position = position;
    }

    abstract Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;

    abstract Figure copy(Cell dest);
}