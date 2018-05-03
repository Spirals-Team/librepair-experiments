package ru.job4j.strategy;

public class Paint {
    private  Shape shape;

    public void draw(Shape shape) {
        System.out.println(shape.draw());
    }
}

