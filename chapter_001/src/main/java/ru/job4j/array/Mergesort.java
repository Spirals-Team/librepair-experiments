package ru.job4j.array;

import java.util.Arrays;

public class Mergesort {


    /**
     * Слияние двух отсортированных массивов в третий отсортированный
     * @param left первый массив
     * @param right второй массив
     * @return результирующий массив
     *
     */

    public int[] msort(int[] left, int[] right) {

        // определяем результирующий массив суммарной длины 2х массивов
        int[] resarr = new int[left.length + right.length];

        // инициализируем счетчики 3х массивов
        int i= 0;
        int j=0;
        int k=0;

        //сравниваем элементы массива попарно, пока не кончиться один из массивов
        while (i < left.length && j < right.length)
            resarr[k++]=left[i]<=right[j]?left[i++]:right[j++];

        // смотрим какой из массивов первым закончился и копируем остатки второго массива в результирующий
        if (i == left.length )
            for (int m=j;m<=right.length-1;m++)
                resarr[k++]=right[m];
        else
            for (int m=i;m<=left.length-1;m++)
                resarr[k++]=left[m];

        return resarr;
    }
}
