package ru.job4j.chess.figure;

import ru.job4j.chess.behavior.IChessMove;
import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ChessFigure extends Figure {
    private final IChessMove type;
    public ChessFigure(Cell cell, IChessMove type) {
        super(cell);
        this.type = type;
    }
    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        return type.way(source, dest);
    }
    @Override
    public Figure copy(Cell dest) {
        return new ChessFigure(dest, type);
    }
}
