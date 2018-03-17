package ru.job4j.litle;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
/**
 * Test.
 * @author Hincu Andrei (andreih1981@gmail.com) by 17.09.17;
 * @version $Id$
 * @since 0.1
 */
public class ConvertListTest {
    /**
     *Тест конвертации двумерного массива в ArrayList.
     */
    @Test
    public void whenConvertArrayToArrayList() {
        ConvertList convertList = new ConvertList();
        int[][]array = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        ArrayList<Integer> list = (ArrayList) convertList.toList(array);
        ArrayList<Integer> ex = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                ex.add(array[i][j]);
            }
        }
        assertThat(list, is(ex));
    }

    /**
     * Test сонвертации ArrayList в двумерный массив с заполнением пустых ячеек нулями.
     */
    @Test
    public void whenConvertArrayListToArray() {
        ConvertList convertList = new ConvertList();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list.add(i);
        }
        int[][]array = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 0, 0}
        };
        int[][]result = convertList.toArray(list, 3);
        assertThat(result, is(array));
    }

    /**
     * Test Конвертации листа массивов в один лист Integer.
     */
    @Test
    public void whenConvertListWishArraysThenListOfIntegers() {
        ConvertList convertList = new ConvertList();
        ArrayList<int[]> list = new ArrayList<int[]>();
        list.add(new int[]{1, 2, 3});
        list.add(new int[]{4, 5});
        list.add(new int[]{6, 7, 8, 9});
        ArrayList<Integer> ex = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            ex.add(i);
        }
        ArrayList<Integer> result = (ArrayList<Integer>) convertList.convert(list);
        assertThat(result, is(ex));
    }

    /**
     * Test.
     */
    @Test
    public void whenConvertArrayListWithNullThenArray() {
        ConvertList convertList = new ConvertList();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(null);
        list.add(4);
        int[] result = convertList.convert1(list);
        int[] ex = {1, 2, 0, 4};
        assertThat(result, is(ex));
    }
}










