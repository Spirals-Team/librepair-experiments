package ru.job4j.tracker;

public class ValidateInput implements Input {
private final Input input;

    public ValidateInput(Input input) {
        this.input = input;
    }


    @Override
    public String inputCommand(String command) {
        return this.input.inputCommand(command);
    }

    @Override
    public int inputCommand(String command, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
               value = this.input.inputCommand(command, range);
               invalid = false;
            } catch (MenuOutException tes) {
                System.out.println("Пожалусто выберете ключ меню");
            } catch (NumberFormatException nfe) {
                System.out.println("Ошибка, введите корректные данные");
            }
        } while (invalid);
        return  value;
    }
}
