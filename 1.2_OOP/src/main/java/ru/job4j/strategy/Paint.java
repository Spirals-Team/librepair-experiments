package ru.job4j.strategy;
/**
 *Класс Paint.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
public class Paint {
    /**
     * Основной метод. Рисует на экран фигуру в зависимости от объекта.
     * @param shape фигура, которую будем рисовать.
     */
    public void draw(Shape shape) {
        System.out.println(shape.pic());
    }
}