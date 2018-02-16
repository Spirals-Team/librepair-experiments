package ru.job4j.chess.exceptions;
/*** OccupiedWayException.*/
public class OccupiedWayException extends Exception {
    /**
     * Constructor.
     * @param message - message
     */
    public OccupiedWayException(String message) {
        super(message);
    }
}
