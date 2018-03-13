package ru.job4j.generic;

import java.lang.reflect.ParameterizedType;

//можем ограничивать тип наследуясь
//public class SimpleList<E extends Number> {
//public class SimpleList<E > {
public class SimpleList<T> {



    Object[] objects; // = new Object[10];
    int index = 0;

    public SimpleList(int size) {
        this.objects = new Object[size];

//        T value = new T();//на прямую нельзя
//        Class t = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass();
        Class t = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        try {
            T value = (T) t.newInstance();
            System.out.printf("string " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public <K> K print(K value) {
        System.out.println(value);
        return value;
    }

//    public void add(E value) {
//        this.objects[index++] = value;
//    }
//
//    public E get(int position) {
//        return (E) this.objects[position];
//    }

    public void add(T value) {
        this.objects[index++] = value;
    }

    public T get(int position) {
        return (T) this.objects[position];
    }



}
