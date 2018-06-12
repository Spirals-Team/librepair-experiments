package ru.job4j.chess.behavior;

import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public interface IChessMove {
     Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;
}
