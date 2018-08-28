package ru.job4j.tracker.start;

import java.util.ArrayList;

/**
 * Сlass StubInput.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 08.10.2017
 */
public class StubInput implements Input {
    /**
     * Массив.
     */
    private String[] answers;
    /**
     * Переменная позиции.
     */
    private int position = 0;

    /**
     * Конструктор.
     *
     * @param answers Принимаем массив.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     * Метод ask.
     *
     * @param question Принимаем String.
     * @return Вернем следующий элемент массива.
     */
    @Override
    public String ask(String question) {
        return answers[position++];
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

    /**
     * Метод getKey.
     *
     * @return Вернем текущий ответ.
     */
    @Override
    public String getKey() {
        return answers[position - 1];
    }
}
