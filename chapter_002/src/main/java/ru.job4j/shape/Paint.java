package ru.job4j.shape;

/**
 * Class Paint.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 09.10.2017
 */
public class Paint {
    /**
     * Принимаем любую реализацию фигуры.
     *
     * @param shape выводим на экран фигуру.
     */
    public void draw(Shape shape) {
        System.out.println(shape.pic());
    }
}
