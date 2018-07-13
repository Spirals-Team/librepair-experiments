package ru.job4j.convert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConvertListTest {

    /**
     * Тест перевода массива в список.
     * Тест сравнивает размер полученного списка с количеством элементов массива
     */
    @Test
    public void getArrayToList() {
        Random randomGenerator = new Random();
        // количество строк в массиве
        final int sizer=3;
        // количество столбцов в массиве
        final int sizec=3;
        // инициализируем массив
        int[][] source=new int[sizer][sizec];
        // заполняем массив случайными целыми
        for (int i=0; i< sizer; i++) {
            for (int j=0; j<sizec; j++) {
                source[i][j]=randomGenerator.nextInt();
            }
        }
        List<Integer> result= new ArrayList<>();
        result=ConvertList.toList(source);
        // сравниваем размер списка и массива
        assertThat(result.size(), is(sizer*sizec));
    }

    /**
     * Тест перевода списка в массив.
     * Тест сравнивает значение одного и тогоже элемента в списке и массиве
     */
    @Test
    public void getListToArray() {
        Random randomGenerator = new Random();
        // размер списка
        final int size=16;
        List<Integer> source = new ArrayList<>();
        // заполняем список случайными значениями
        for (int i=0; i<size;i++ ) {
            source.add(randomGenerator.nextInt());
        }
        // определяем массив
        int[][] result;
        result=ConvertList.toArray(source, 3);
        // сравниваем значения элемента
        assertThat(result[2][1], is(source.get(13)));
    }

    /**
     * Тест перевода списка массивов в один список.
     */
    @Test
    public void getListOfArrayConvertToList() {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2});
        list.add(new int[]{3, 4, 5, 6});
        List<Integer> result = ConvertList.Convert(list);
        assertThat(result, is(Arrays.asList(1, 2, 3, 4, 5, 6)));
    }
}