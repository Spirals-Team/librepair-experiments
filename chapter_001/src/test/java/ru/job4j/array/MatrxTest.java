package ru.job4j.array;

/**
 * @author Alexander Kaleganov
 * @version 1.0
 * создали двухмерный массив, и заполнили его
 * вывовдим его в консоль чтобы посмотреть что получилось
 * добавили переход на новую строку
 * добавим услови , если значение меньше 10, то добавим три пробела иначе добвим два пробела
 */

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MatrxTest {
    @Test
    public void proverkaClassaMatrix() {
        int n = 5;
        int[][] multi = new Matrix().multiple(n);
        for (int i = 0; i < multi.length; i++) {
            System.out.println(" ");
            for (int j = 0; j < multi.length; j++) {
                System.out.print(multi[i][j]);
                if (multi[i][j] < 10) {
                    System.out.print("   ");
                } else {
                    System.out.print("  ");
                }
            }
        }
        int[][] expected = {
                {1, 2, 3, 4, 5},
                {2, 4, 6, 8, 10},
                {3, 6, 9, 12, 15},
                {4, 8, 12, 16, 20},
                {5, 10, 15, 20, 25}};
        assertThat(multi, is(expected));
    }
}
