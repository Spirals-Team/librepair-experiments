package ru.job4j.tracker;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ValidateInput extends ConsoleInput {

    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Введите значение меню от 0 до 6.");
            } catch (NumberFormatException nfe) {
                System.out.println("Введите корректные данные.");
            }
        } while (invalid);
        return value;
    }
}
