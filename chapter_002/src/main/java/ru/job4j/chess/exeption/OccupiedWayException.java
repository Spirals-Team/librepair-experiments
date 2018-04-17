package ru.job4j.chess.exeption;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class OccupiedWayException extends Exception {
    public OccupiedWayException(String msg) {
        super(msg);
    }
}
