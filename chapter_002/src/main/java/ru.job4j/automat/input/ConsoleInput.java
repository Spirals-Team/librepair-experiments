package ru.job4j.automat.input;

import java.util.Scanner;

/**
 * Class ConsoleInput.
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
}
