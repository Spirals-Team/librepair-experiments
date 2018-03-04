package ru.job4j.tracker;
/**
 * Input.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public interface Input {
    /**
     * Ask.
     * @param question - question
     * @return scanner
     */
    String ask(String question);
    /**
     * Ask with range.
     * @param question - question
     * @param range - range
     * @return - scanner
     */
    int ask(String question, int[] range);
}
