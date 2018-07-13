package ru.job4j.pseudo;

// класс-прокладка, который и реализует единую печать для всех классов, использующих интерфейс Форма

public class Paint {
    public void draw(Shape shape) {
        System.out.println(shape.draw());
    }
}
