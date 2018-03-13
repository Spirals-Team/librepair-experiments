package ru.job4j.chess;

public class FigureUnknown extends Figure {

    FigureUnknown(Cell position) {
        super(position);
    }

    @Override
    Cell[] way(Cell dist) throws ImposibleMoveException {
        return new Cell[0];
    }

    @Override
    Figure clone(Cell dist) {
        return null;
    }
}
