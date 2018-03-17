package ru.job4j.tracker.input;

import ru.job4j.tracker.start.MenuOutputException;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.09.17;
 * @version $Id$
 * @since 0.1
 */
public class ValidateInput extends ConsoleInput {
    /**
     * Метод с обработкой ошибок.
     * @param question вопрос.
     * @return ответ.
     */
    @Override
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = 0;
        do {
            try {
                value = super.ask(question, range);
                invalid = false;
            } catch (MenuOutputException e) {
                System.out.println("Please select key from menu.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }
}
