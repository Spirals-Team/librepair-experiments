package ru.job4j.chessBoard;

public class ImpossibleMoveException extends RuntimeException {
    public ImpossibleMoveException(String msg) {
        super(msg);
    }
}
