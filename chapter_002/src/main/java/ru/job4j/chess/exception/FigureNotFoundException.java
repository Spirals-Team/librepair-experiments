package ru.job4j.chess.exception;

/**
 * в заданной ячейки фигура отсутствует
 */

public class FigureNotFoundException extends UnsupportedOperationException {

    public FigureNotFoundException() {
        super("В заданной ячейке фигура отсутствует");
    }
}
