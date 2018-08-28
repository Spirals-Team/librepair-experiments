package ru.job4j.chess;

import ru.job4j.chess.exception.FigureNotFoundException;
import ru.job4j.chess.exception.ImpossibleMoveException;
import ru.job4j.chess.exception.OccupiedWayException;

public class Board {
    private Figure[] figures;

    public Board(Figure[] figures) {
        this.figures = figures;
    }

    boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        return false;
    }
}