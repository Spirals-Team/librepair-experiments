package ru.job4j.max;

/**
 * author Daniil Emelyanov
 * @version $id$
 * @since  12/03/2018
 */
public  class Max {
    /**
     * Метод возвращающий максимум их двух чисел.
     *
     * @param first  первое значение
     * @param second второе значение
     * @return наибольшое значение.
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    }
    /**
     * Метод возвращающий максимум их трех чисел.
     *
     * @param first  первое значение
     * @param second второе значение
     * @param third третье значене
     * @return наибольшое значение.
     */
    public int max(int first, int second, int third) {
        int temp = this.max(first, second);
        return  max((temp), third);
    }
}
