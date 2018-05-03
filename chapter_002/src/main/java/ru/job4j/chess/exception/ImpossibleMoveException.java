package ru.job4j.chess.exception;

/**
 * ошибка фигура туда пойти не может
 */

public class ImpossibleMoveException extends UnsupportedOperationException {


    public ImpossibleMoveException() {
        super("Фигура туда пойти не может");
    }
}
