package ru.job4j.tracker;

import java.util.Scanner;
/**
 * Console input.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class ConsoleInput implements Input {
    /*** Scanner.*/
    private Scanner scanner = new Scanner(System.in);
    /**
     * Ask.
     * @param question - question
     * @return scanner
     */
    @Override
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }
    /**
     * Ask with range.
     * @param question - question
     * @param range - range
     * @return - scanner
     */
    @Override
    public int ask(String question, int[] range) {
        int key = Integer.parseInt(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Out of menu range!");
        }
    }
}

