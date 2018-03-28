package ru.job4j.chessBoard;
import java.lang.Math;

public class Bishop extends Figure {
    public Bishop(Cell dest) {
        super(dest);
    }

    @Override
    Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int delta = Math.abs(source.x - dest.x);
        Cell[] result = new Cell[delta];
        for (int i = 0; i < 8; i++) {
            if (((source.x == dest.x + i) && (source.y == dest.y + i)) || ((source.x == dest.x - i) && (source.y == dest.y - i))) {
                for (int j = 0; j < delta; j++) {
                    result[j] = new Cell(j, j);
                }
                return result;
            }
        }
        throw new ImpossibleMoveException("Impossible move!");
    }

    @Override
    Figure copy(Cell dest) {
        return new Bishop(dest);
    }
}
