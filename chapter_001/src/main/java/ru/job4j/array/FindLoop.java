package ru.job4j.array;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class FindLoop {
    /**
     * Метод возвращает индекс искомого элемента массива
     * @param data массив в котором организуется поиск
     * @param el искомый элемент
     * @return rsl - индекс найденного элемента
     * @see FindLoop
     */
    public int indexOf(int[] data, int el) {

        int rsl = -1;

        for (int index = 0; index < data.length; index++) {
            if (data[index] == el) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}