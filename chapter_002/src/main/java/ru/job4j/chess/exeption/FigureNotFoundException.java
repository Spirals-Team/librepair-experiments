package ru.job4j.chess.exeption;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class FigureNotFoundException extends Exception {
    public FigureNotFoundException(String msg) {
        super(msg);
    }
}
