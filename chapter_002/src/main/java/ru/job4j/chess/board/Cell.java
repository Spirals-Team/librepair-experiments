package ru.job4j.chess.board;

import ru.job4j.chess.exeption.ImpossibleMoveException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Cell {
    private int x;
    private int y;
    /**
     * Конструктор принимает координаты X(горизонтальная)[1, 8], Y(вертикальная)[1, 8].
     * Начало координат в нижнем левом углу.
     */
    public Cell(int x, int y) throws ImpossibleMoveException {
        if (x > 8 || y > 8) {
            throw new ImpossibleMoveException("Недопустимый ход/начальное положение фигуры");
        } else {
            this.x = x;
            this.y = y;
        }
    }
    /**
     * Конструктор принимает название ячейки(пример a1, b3 и т.д.)
     * и уст. в полях x и y, соотв. ячейки, координаты
     */
    public Cell(String s) throws ImpossibleMoveException {
        this.x = converter(s)[0];
        this.y = converter(s)[1];
    }

    /**
     * Метод преобразующий ячейки вида a1, b3 и т.д. к координатам
     */
    public static int[] converter(String s) throws ImpossibleMoveException {
        char ch = s.charAt(0);
        int y = Integer.parseInt(s.substring(1));
        int x;
        switch (ch) {
            case 'a':
                x = 1;
                break;
            case 'b':
                x = 2;
                break;
            case 'c':
                x = 3;
                break;
            case 'd':
                x = 4;
                break;
            case 'e':
                x = 5;
                break;
            case 'f':
                x = 6;
                break;
            case 'g':
                x = 7;
                break;
            case 'h':
                x = 8;
                break;
            default:
                x = 9;
                break;
        }
        if (x > 8 || y > 8) {
            throw new ImpossibleMoveException("Недопустимый ход/начальное положение фигуры");
        } else {
            return new int[] {x, y};
        }
    }
    public boolean cellEquals(Cell cell) {
        boolean result = false;
        if (this.x == cell.x && this.y == cell.y) {
            result = true;
        }
        return result;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
