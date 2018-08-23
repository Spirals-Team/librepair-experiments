package ru.job4j.chess;

import ru.job4j.chess.figure.Figure;
/**
 *Класс Cell. Клетка шахматной доски.
 *@author ifedorenko
 *@since 30.08.2017
 *@version 1
 */
public class Cell {
    /**
     * @param busy возвращает результат в зависимости от того, есть ли в ячейке фигура.
     */
    private boolean busy;
    /**
     * @param figure хранит информацию о фигуре, находящейся в данной ячейке.
     */
    private Figure figure;
    /**
     * @param x координата по оси x (принимает значения от A до H)
     */
    private char x;
    /**
     * @param y координата по оси y (принимает значения от 1 до 8)
     */
    private char y;
    /**
     * @param color цвет ячейки
     */
    private String color;
    /**
     * @param name имя ячейки
     */
    private String name;
    /**
     * @param board шахматная доска
     */
    private Board board;

    /**
     * Конструктор ячейки.
     * @param board our board
     * @param x координата по оси x
     * @param y координата по оси y
     * @param color цвет ячейки
     */
    public Cell(Board board, char x, char y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = new StringBuilder().append(this.x).append(this.y).toString();
        this.board = board;
    }

    /**
     * Метод возвращает переменную x.
     * @return x
     */
    public char getX() {
        return this.x;
    }
    /**
     * Метод возвращает переменную y.
     * @return y
     */
    public char getY() {
        return this.y;
    }

    /**
     * Метод возвращает имя ячейки.
     * @return name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Метод возвращает информацию о шахматной фигуре, находящейся на данной клетке.
     * @return возвращает информацию.
     */
    public Figure getFigure() {
        return this.figure;
    }

    /**
     * Метод добавлят информацию о шахматной фигуре.
     * @param figure информация о шахматной фигуре.
     */
    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    /**
     * Getter.
     * @return return our board
     */
    public Board getBoard() {
        return board;
    }
}
