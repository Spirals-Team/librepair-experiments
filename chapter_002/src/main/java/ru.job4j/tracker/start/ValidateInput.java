package ru.job4j.tracker.start;

import java.util.ArrayList;

/**
 * Унаследованный клас от ConsoleInput.
 */
public class ValidateInput extends ConsoleInput {
    /**
     * Переопределяем метод ask для обработки ошибок ввода.
     * @param question принимаем сообщение.
     * @param range принимаем массив доступных ответов.
     * @return вернем значение меню.
     */
    @Override
    public int ask(String question, ArrayList<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Pleas  select key from menu. ");
            } catch (NumberFormatException nfe) {
                System.out.println("Pleas enter validate data again. ");
            }
        } while (invalid);

        return value;
    }
}
