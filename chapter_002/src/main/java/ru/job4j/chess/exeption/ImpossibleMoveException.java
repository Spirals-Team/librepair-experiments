package ru.job4j.chess.exeption;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ImpossibleMoveException extends Exception {
    public ImpossibleMoveException(String msg) {
        super(msg);
    }
}
