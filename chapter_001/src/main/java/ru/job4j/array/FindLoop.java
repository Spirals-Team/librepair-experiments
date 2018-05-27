package ru.job4j.array;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class FindLoop {

    /**
     * Метод поиска элемента в массиве.
     * @param data - массив в котором ищем.
     * @param el - элемент который ищем.
     * @return - если находим, возвращаем индекс где находися элемент, иначе возвращаем -1.
     */
    public int indexOf(int[] data, int el) {
        int rsl = -1; // если элемента нет в массиве, то возвращаем -1.
        for (int i = 0; i < data.length; i++) {
            if (data[i] == el) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
