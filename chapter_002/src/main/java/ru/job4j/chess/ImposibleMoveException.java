package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class ImposibleMoveException extends RuntimeException {

    public ImposibleMoveException(String msg) {
        super(msg);
    }
}
