package ru.job4j.tracker;

public interface Input {
    String inputCommand(String command);
    int inputCommand(String command, int[] range);
}
