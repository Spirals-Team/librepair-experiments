package ru.job4j.start;

/**
 *
 */
public interface Input {
    /**
     *
     * @param question String question
     * @return String answer
     */
    String ask(String question);

    /**
     * Перегрузить метод ask ( int ask(String question, int[] range); ) в интерфейсе Input.
     * @param question
     * @param range
     * @return
     */
    int ask(String question, int[] range);

}
