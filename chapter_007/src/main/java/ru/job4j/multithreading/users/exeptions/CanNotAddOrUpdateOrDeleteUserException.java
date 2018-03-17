package ru.job4j.multithreading.users.exeptions;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.11.17;
 * @version $Id$
 * @since 0.1
 */
public class CanNotAddOrUpdateOrDeleteUserException extends RuntimeException {
    public CanNotAddOrUpdateOrDeleteUserException(String s) {
        super(s);
    }
}
