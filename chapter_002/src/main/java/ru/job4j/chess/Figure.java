package ru.job4j.chess;

import ru.job4j.chess.exception.ImpossibleMoveException;

import java.util.Random;

/**
 * @author Alexander KAleganov
 * тут мы будем описывать абстрактное поведение игуры
 * как я понял абстрактные классы это практически те же интерфейсы, только мы
 * можем реализовывать методы
 */
public abstract class Figure {
    /**
     * автоматическая генерация положения фигыру на доске
     */
    Random rn = new Random();
    private int x = rn.nextInt(8) + 1;
    private int y = rn.nextInt(8) + 1;
    private Cell start = new Cell(x, y);

    private Cell begincoordinat;

    /**
     * если мы не хотим изначально указывать клетку фигуры
     * то генератор её сам сгенерирует
     */
    Figure() {
        this.begincoordinat = start;
    }

    Figure(Cell begincoordinat) {
        this.begincoordinat = begincoordinat;
    }

    /**
     * рамндомная генерация положения фигуры
     */
    public void randomGeneratorfiger(Figure figure) {
        figure.begincoordinat = start;
    }

    /**
     * может ли фигура пойти по заданым координатам если да, то вернуть пройденный путь
     *
     * @param source
     * @param dest
     * @return
     * @throws ImpossibleMoveException
     */
    abstract Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;

    abstract Figure figureCopy(Cell dest);

    /**
     * переопределим метод экуалс, зараза ссылки сравнивает
     * этим макаром мы сможем сравнивать между собой все слассы наследникик
     * будь то слон, будь то ладья или другие фигуры, он удет сравнивать только координаты
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean test = true;
        if (obj instanceof Figure) {

            Figure valid = (Figure) obj;
            if (obj != null && begincoordinat.getX() == valid.begincoordinat.getX() && begincoordinat.getY() == valid.begincoordinat.getY()) {
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
     * переопределим метод хеш код от класса обжект
     * так же как и хеш код координат возвращает интовое значение которое будет идентично
     * так получится сравнивать абсолютно разные классы , а именно параметры классов
     * изначально я через тостринг начал сравнивать с помощью contains  но совсем забыл про то что можно также переопределить
     * методы хеш код и  экуалс для удобства
     *
     * @return
     */
    @Override
    public int hashCode() {
        return this.begincoordinat.getX() * 10 + this.begincoordinat.getY();
    }

    /**
     * переопределим метод toString  от класса Object для удобстав вывода
     *
     * @return
     */
    @Override
    public String toString() {
        return "клетка, занимаемая aигурой " + "X: " + begincoordinat.getX() + "; Y: " + begincoordinat.getY();
    }
}
