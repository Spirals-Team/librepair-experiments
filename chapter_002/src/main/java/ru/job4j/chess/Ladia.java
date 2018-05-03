package ru.job4j.chess;
/**
 * класс ладья для создания объекта ладья
 * пока не доработан, создавался для тестов и эксперементов
 */

import ru.job4j.chess.exception.ImpossibleMoveException;

public class Ladia extends Figure {


    public Ladia(Cell begincoordinat) {
        super(begincoordinat);
    }

    public Ladia() {
        super();
    }

    /**
     * возрващает путь пройденный фигурой
     *
     * @param source
     * @param dest
     * @return
     * @throws ImpossibleMoveException
     */
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        Cell[] result = new Cell[8];
        int x = source.getX();
        int y = source.getY();
        boolean exit = true;
        for (int i = 0; i < result.length; i++) {
            if (source.hashCode() == dest.hashCode()) {
                result[0] = source;
                break;
            } else if (source.getX() != dest.getX() && source.getY() == dest.getY()) {
                result[i] = new Cell(x < dest.getX() ? x++ : x--, y);
            } else if (source.getX() == dest.getX() && source.getY() != dest.getY()) {
                result[i] = new Cell(x, y < dest.getY() ? y++ : y--);
            } else {
                exit = false;
            }
        }
        if (exit) {
            return result;
        } else {
            throw new ImpossibleMoveException();
        }
    }

    /**
     * возвращает новую фигуру с новыми координатами
     *
     * @param dest
     * @return
     */
    public Figure figureCopy(Cell dest) {
        Ladia ladia = new Ladia(dest);
        return ladia;
    }
}
