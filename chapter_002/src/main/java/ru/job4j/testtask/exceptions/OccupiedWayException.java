package ru.job4j.testtask.exceptions;

/**
 * Занятая ячейка.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 09.09.17;
 * @version $Id$
 * @since 0.1
 */
public class OccupiedWayException extends RuntimeException {
    /**
     * Constructor.
     * @param s message.
     */
    public OccupiedWayException(String s) {
        super(s);
    }
}
