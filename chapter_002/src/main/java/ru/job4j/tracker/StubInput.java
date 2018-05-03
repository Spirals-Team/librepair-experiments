package ru.job4j.tracker;

public class StubInput implements Input {
    private final String[] value;
    private int position = 0;

    public StubInput(final String[] value) {
        this.value = value;
    }
    @Override
    public String inputCommand(String command) {
        return this.value[position++];
    }

    @Override
    public int inputCommand(String command, int[] range) {
        int key = Integer.valueOf(this.inputCommand(command));
        boolean exit = false;
        for (int value:  range) {
            if (value == key) {
                exit = true;
                break;
            }
        } if (exit) {
            return key;
        } else {
             throw  new UnsupportedOperationException("Пункта меню не существует");
        }
    }

}
