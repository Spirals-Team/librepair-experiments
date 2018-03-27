package ru.job4j.testtask.exceptions;

/**
 * FigureNotFoundException.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 09.09.17;
 * @version $Id$
 * @since 0.1
 */
public class FigureNotFoundException extends RuntimeException {
    /**
     * Constructor.
     * @param s message.
     */
   public FigureNotFoundException(String s) {
        super(s);
    }
}
