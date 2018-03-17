package ru.job4j.tracker.input;

/**
 *StubInput.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 06.09.17;
 * @version $Id$
 * @since 0.1
 */
public class StubInput implements Input {
    /**
     * Массив ответов.
     */
    private String[]answers;
    /**
     * Позиция ответа.
     */
    private int  pozition;

    /**
     * Конструктор класса.
     * @param answers массив ответов.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     * Метод ask для эмуляции поведения пользователя.
     * @param question вопрос.
     * @return ответ соответствующий номеру вопроса.
     */
    @Override
    public String ask(String question) {
        return answers[pozition++];
    }

    /**
     * Сообщение.
     * @param message сообщение.
     */
    @Override
    public void writeMessage(String message) {
        System.out.println(message);
    }

    @Override
    public int ask(String quesction, int[] range) {
        int key = Integer.parseInt(this.ask(quesction));
        boolean exists = false;
        for (int ranges : range) {
            if (ranges == key) {
                exists = true;
                break;
            }
        }
        return exists ? key : -1;
    }
}
