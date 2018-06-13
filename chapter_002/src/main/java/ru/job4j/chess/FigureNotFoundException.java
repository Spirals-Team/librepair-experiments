package ru.job4j.chess;

/**
 * @version $Id$
 * @since 0.1
 */

class FigureNotFoundException extends RuntimeException {
    FigureNotFoundException(String msg) {
        super(msg);
    }
}
