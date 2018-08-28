package ru.job4j.tracker.start;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class ConsoleInput.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 07.10.2017
 */
public class ConsoleInput implements Input {
    /**
     * Объект сканер принимающий ответ от бользователя.
     */
    private Scanner scanner = new Scanner(System.in);
    /**
     * Переменная.
     */
    private String key = "";

    /**
     * Метод возвращающий ответ пользователя.
     *
     * @return вернем сохраненный ответ.
     */
    public String getKey() {
        return key;
    }

    /**
     * Метод для диалога с ользователем.
     *
     * @param question принимаем сообщение для пользователя.
     * @return Возвращем ответ от пользователя.
     */
    @Override
    public String ask(String question) {
        System.out.println(question);
        key = scanner.nextLine();
        return key;
    }

    @Override
    public int ask(String question, ArrayList<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
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
            throw new MenuOutException("Out of menu range. ");
        }
    }
}
