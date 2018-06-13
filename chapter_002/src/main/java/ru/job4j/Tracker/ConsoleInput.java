package ru.job4j.tracker;

import java.util.*;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.2
 * @since 0.1
 */

public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    /**
     * функция, запрашивающая ввод от пользователя
     * @param question отображаемый вопрос
     * @return возвращает введенный ответ пользователя
     */
    @Override
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    /**
     * функция, проверяющая корректность вводимых пользователем данных
     * @param question информация для пользователя о требуемых данных
     * @param range массив допустимых значений для ввода
     * @return возвращает корректный ввод от пользователя
     */
    @Override
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (key == value) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Выход за границы допустимых значений");
        }
    }
}
