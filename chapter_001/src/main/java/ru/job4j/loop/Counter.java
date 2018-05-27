package ru.job4j.loop;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Counter {

    /**
     * Метод вычисляет сумму четныx чисел в диапазоне от start до finish.
     * @param start - начальное значение.
     * @param finish - конечное значение.
     * @return возвращает сумму четный чистел или -1 если finish меньше чем start.
     */
    public int add(int start, int finish) {
        int sum = 0;
        if (finish < start) {
            sum = -1;
        } else {
            for (int i = start; i <= finish; i++) {
                if (i % 2 == 0) {
                    sum += i;
                }
            }
        }
        return sum;
    };
}
