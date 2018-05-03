package ru.job4j.chess;

import java.util.Random;

public class Cell {
    private int x;
    private int y;
    private Random rn = new Random();

    /**
     * это у нас будет конструктор ИМЕННО для ввода координат
     * если координаты будут больше 8, то будет присваиваться максимальное значение 8
     * если меньше 1 , то будет присваиваться минимальное значение 1
     * это к теме об исключения, что подобного рода ошибки программист должен отлавливать в самом начале
     *
     * @param x
     * @param y
     */
    public Cell(int x, int y) {
        if (x < 1) {
            this.x = 1;
        } else if (x > 8) {
            this.x = 8;
        } else {
            this.x = x;
        }
        if (y < 1) {
            this.y = 1;
        } else if (y > 8) {
            this.y = 8;
        } else {
            this.y = y;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * для координат тоже переопределим метод экуалс чтобы можно было сравнивать координаты между собой
     * в противном случае он сравнивает ссылки, а ссылка одний координаты отличается от другой
     * даже если их параметры равны, т.к. это совершенно равные объекты и они не могут быть идентичными
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean test = true;
        if (obj instanceof Cell) {
            test = true;
            Cell valid = (Cell) obj;
            if (obj != null && getX() == valid.getX() && getY() == valid.getY()) {
                test = true;
            } else {
                test = false;
            }
        } else {
            test = false;
        }
        return test;
    }

    /**
     * мне иногда необходимо будет проверять находится ли объект по заданным координатам,
     * для этого разумней использовать хешь код, только мы его переопределим,
     * чтобы получать хешь код двухзначное число первое число будет Х второе Y
     * вычисляться будет так: X * 10 + Y = res
     *
     * @return
     */
    @Override
    public int hashCode() {
        return this.x * 10 + this.y;
    }

    @Override
    public String toString() {
        return "X: " + this.x + "; Y: " + this.y;
    }
}
