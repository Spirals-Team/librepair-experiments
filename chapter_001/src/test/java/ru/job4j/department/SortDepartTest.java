package ru.job4j.department;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SortDepartTest {

    // список отделов в виде списка
    ArrayList<Depart> departs = new ArrayList<>();
    SortDepart sdepart = new SortDepart();
    // список отделов в виде текста
    String [] departS = {
            "K1/SK1",
            "K1/SK2",
            "K1/SK1/SSK1",
            "K1/SK1/SSK2",
            "K2",
            "K2/SK1/SSK1",
            "K2/SK1/SSK2"
    };


    /**
     * Конвертируем текстовый список в List
     */
    @Before
    public void initArray() {
        departs = sdepart.convert(departS);
    }

    /**
     * Тестируем дополнения списка отделов недостающими отделами
     * Тест проверяет элемент из результирующего списка на 7й позиции
     */
    @Test
    public void normalization() {
        System.out.println("Исходный список:");
        for (Depart dep : departs) {
            System.out.println(dep);
        }
        departs = sdepart.normalization(departs);
        System.out.println("Список после добавления:");
        for (Depart dep : departs) {
            System.out.println(dep);
        }
        System.out.println("-------------------------");
        assertThat(departs.get(7).equals(new Depart(new String[]{"K1"})), is(true));
    }

    /**
     * Тестируем сортировку списка отделов по возрастанию
     * Этот порядок заложен у нас в качестве natural order
     * Тест проверяет что 7й элемент списка меньше 8го
     */
    @Test
    public void sortNatural() {
        departs = sdepart.normalization(departs);
        System.out.println("Исходный список:");
        for (Depart dep : departs) {
            System.out.println(dep);
        }
        departs = sdepart.sortNatural(departs);
        System.out.println("Список после сортировки по возрастанию:");
        for (Depart dep : departs) {
            System.out.println(dep);
        }
        System.out.println("-------------------------");
        assertThat(departs.get(7).compareTo(departs.get(8)) == -1, is(true));
    }

    /**
     * Тестируем сортировку списка отделов по убыванию
     * Тест проверяет что 7й элемент списка больше 8го
     */
    @Test
    public void sortDec()  {
        departs = sdepart.normalization(departs);
        System.out.println("Исходный список:");
        for (Depart dep : departs) {
            System.out.println(dep);
        }
        departs = sdepart.sortDec(departs);
        System.out.println("Список после сортировки по убыванию:");
        for (Depart dep : departs) {
            System.out.println(dep);
        }
        System.out.println("-------------------------");
        assertThat(departs.get(7).compareTo(departs.get(8)) == 1, is(true));
    }
}