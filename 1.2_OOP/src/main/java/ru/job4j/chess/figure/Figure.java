package ru.job4j.chess.figure;

import ru.job4j.chess.Cell;
import ru.job4j.chess.ImpossibleMoveException;

/**
 *Абстрактный класс Figure.
 *@author ifedorenko
 *@since 30.08.2017
 *@version 1
 */
public abstract class Figure {
    protected final Cell position;
    protected final String color;

    /**
     * Constructor.
     * @param position our postion on the board
     * @param color our color
     */
    public Figure(Cell position, String color) {
        this.position = position;
        this.position.setFigure(this);
        this.color = color;
    }
    /**
     * Abstract Way.
     * @param dist our target cell
     * @return array of cells on the way
     * @throws ImpossibleMoveException Bishop can't move
     */
    public abstract Cell[] way(Cell dist) throws ImpossibleMoveException;
    /**
     * Abstract Clone.
     * @param dist our target cell
     */
    public abstract void clone(Cell dist);
}


