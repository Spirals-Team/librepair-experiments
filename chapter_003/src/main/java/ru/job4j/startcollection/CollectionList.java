package ru.job4j.startcollection;

import java.util.*;

import static java.lang.String.format;

/**
 * /тестирование интерфеса List
 */
public class CollectionList {
    static class Node {
        Node next;
    }

    public static void main(String[] args) {

        Node first = new Node();
        Node second = new Node();
        Node third = new Node();

        first.next = second;
        second.next = third;

        List<Integer> list = new LinkedList<Integer>();
        list.add(425);
        int testint = 2;
        double testdouble = 14.16;
        list.add(testint);
        list.add((int) testdouble);
        Integer value = list.get(0);
        System.out.println(value);
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        list.add(3, 32);
        List<Integer> flats = new ArrayList<Integer>();
        flats.add(1);
        flats.add(2);
        list.addAll(flats);
        System.out.println(format("Выводим интекс элемента, начинаем искать сначала  = %s", list.indexOf(2)));
        System.out.println(format("Выводим интекс элемента, начинаем искать с конца  = %s", list.lastIndexOf(2)));
        list.remove(5); //удаляем объект по индексу
        System.out.println(format("Выводим интекс элемента, начинаем искать с конца  = %s", list.lastIndexOf(2)));
        list.remove(Integer.valueOf(testint)); //удаляем объект
        System.out.println(format("Выводим интекс элемента, начинаем искать сначала  = %s", list.indexOf(2)));
        NavigableSet<Integer> test = new TreeSet<Integer>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(5);
        test.add(6);
        System.out.println(format("выводим E ceiling(N объект)   = %s", test.ceiling(4)));
        System.out.println(format("выводим test.higher   = %s", test.higher(5)));
        System.out.println(format("выводим test.lower(5)   = %s", test.lower(5)));
        Iterator<Integer> iterator = test.iterator();
        while (iterator.hasNext()) {
            System.out.println(format("ща поржом %s", iterator.next()));
        }
    }

}
