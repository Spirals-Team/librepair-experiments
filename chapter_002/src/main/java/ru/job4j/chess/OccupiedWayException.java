package ru.job4j.chess;

/**
 * @version $Id$
 * @since 0.1
 */

class OccupiedWayException extends RuntimeException {
    OccupiedWayException(String msg) {
        super(msg);
    }
}
