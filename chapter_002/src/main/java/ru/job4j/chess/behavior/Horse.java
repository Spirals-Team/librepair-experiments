package ru.job4j.chess.behavior;

import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

import static java.lang.Math.abs;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Horse implements IChessMove {
    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int deltaX = dest.getX() - source.getX();
        int deltaY = dest.getY() - source.getY();
        Cell[] way = new Cell[3];
        if (!((abs(deltaX) == 1 & abs(deltaY) == 2) | (abs(deltaX) == 2 & abs(deltaY) == 1))) {
            throw new ImpossibleMoveException("Недопустимый ход");
        }
        for (int i = 0, mull = 1; i < 2; i++, mull++) {
            if (deltaY > 0 & abs(deltaY) == 2) {
                way[i] = new Cell(source.getX(), source.getY() + mull);
            } else {
                if (deltaY < 0 & abs(deltaY) == 2) {
                    way[i] = new Cell(source.getX(), source.getY() - mull);
                } else {
                    if (deltaX > 0 & abs(deltaX) == 2) {
                        way[i] = new Cell(source.getX() + mull, source.getY());
                    } else {
                        if (deltaX < 0 & abs(deltaX) == 2) {
                            way[i] = new Cell(source.getX() - mull, source.getY());
                        }
                    }
                }
            }
        }
        way[2] = dest;
        return way;
    }
}
