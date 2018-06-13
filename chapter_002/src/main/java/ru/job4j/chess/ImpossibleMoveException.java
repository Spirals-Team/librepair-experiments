package ru.job4j.chess;

/**
 * @version $Id$
 * @since 0.1
 */

class ImpossibleMoveException extends RuntimeException {
    ImpossibleMoveException(String msg) {
        super(msg);
    }
}
