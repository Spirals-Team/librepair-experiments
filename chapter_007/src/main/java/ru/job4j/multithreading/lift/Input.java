package ru.job4j.multithreading.lift;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 12.01.18;
 * @version $Id$
 * @since 0.1
 */
public interface Input extends Runnable {
    boolean checkInsideOrOutside();
    int askPerson(String s);
}
