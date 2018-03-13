package ru.job4j.start;

import java.util.Scanner;

/**
 *
 */
public class ConsoleInput implements Input {

    /**
     *
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     *
     * @param question
     * @return
     */
    @Override
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public int ask(String question, int[] range) {

//        int key = Integer.valueOf(this.ask(question));

        int key = -1;

        try {

            key = Integer.valueOf(this.ask(question));

        } catch (NumberFormatException nfe) {

            System.out.println("Please enter correct value.");
        }

        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
//        return exist ? key : throw new MenuOutException("Out of menu range.");
        if (exist) {
            return key;

        } else {
            throw new MenuOutException("out of menu range.");
        }
//        try {return Integer.valueOf(this.ask(question));}
//            catch(NumberFormatException nnn) {
//            System.out.println("Please enter validate data again.");
//            return -1;
//        }

    }
}
