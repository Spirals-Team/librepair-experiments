package ru.job4j.chess.exceptions;
/*** ImpossibleMoveException.*/
public class ImpossibleMoveException extends Exception {
    /**
     * Constructor.
     * @param message - message
     */
    public ImpossibleMoveException(String message) {
        super(message);
    }
}
