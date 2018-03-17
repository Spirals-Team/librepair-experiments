package ru.job4j.testtask.exceptions;

/**
 * Неправильный ход.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 09.09.17;
 * @version $Id$
 * @since 0.1
 */
public class ImpossibleMoveException extends RuntimeException {
    /**
     * конструктор.
     * @param s сообщения.
     */
    public ImpossibleMoveException(String s) {
        super(s);
    }
}
