package ru.job4j.loop;
/**
 * Counter.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Counter {
    /**
     * Метод вычисляет сумму чисел между заданными параметрами включительно.
     * @param start - начальное число.
     * @param finish - конечное число
     * @return суммы четных чмсел.
     */
    public int add(int start, int finish) {
        int result = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                result += i;
            }
        }
        return result;
    }
}
