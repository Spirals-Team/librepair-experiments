package ru.job4j.tracker;

import java.util.Scanner;

public class ConsoleInput implements Input {
    Scanner scanner = new Scanner(System.in);

    @Override
    public String inputCommand(String command) {
        System.out.println(command);
        return scanner.nextLine();
    }

    @Override
    public int inputCommand(String command, int[] range) {
        int key = Integer.valueOf(this.inputCommand(command));
        boolean exit = false;
        for (int value : range) {
            if (value == key) {
                exit = true;
                break;
            }
        }
        if (exit) {
            return key;
        } else {
            throw new MenuOutException("всё хня, давай по новой");
        }
    }
}
