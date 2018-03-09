package aserdyuchenko;

/**
 * 5.1. Создать программу для сортировки массива методов перестановки.
 * Дано. Массив int values[]. Нужно написать метод который будет сортировать данный массив использую алгоритм пузырька.
 *
 * Bubble sort — простой алгоритм сортировки. Для понимания и реализации этот алгоритм — простейший,
 * но эффективен он лишь для небольших массивов.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 11.09.2016
 */
public class BubbleSort {
/**
 * Преобразование оригинальной строки в массив.
 * @param values - values.
 * @return values - values.
 */
    int[] bubbleSort(int[] values) {
        for (int externalIndex = values.length - 1; externalIndex >= 0; externalIndex--) {
            for (int internalIndex = 0; internalIndex < externalIndex; internalIndex++) {
                if (values[internalIndex] > values[internalIndex + 1]) {
                    int t = values[internalIndex];
                    values[internalIndex] = values[internalIndex + 1];
                    values[internalIndex + 1] = t;
                }
            }
        }
        return values;
    }
}
