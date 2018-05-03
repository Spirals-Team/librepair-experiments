package ru.job4j.chess;

import ru.job4j.chess.exception.FigureNotFoundException;
import ru.job4j.chess.exception.ImpossibleMoveException;
import ru.job4j.chess.exception.OccupiedWayException;

public class Board {
    private Figure[] figures = new Figure[32];
    private int position = 0;

    /**
     * принимает фигуру и проверяет не занята ли данная клетка другой фигурой после добавляет её
     *
     * @param newFigure
     */
    public void add(Figure newFigure) {
        boolean add = false;
        for (int i = 0; i < figures.length; i++) {
            if (!newFigure.equals(figures[i])) {
                add = true;
            } else if (newFigure.equals(figures[i])) {
                System.out.println("извините, данная клетка, " + figures[i] + ", занята другой  фигурой " + figures[i].getClass() + ", фигуру нельзя поставить на клетку");
                add = false;
                break;
            }
        }
        if (add) {
            this.figures[position++] = newFigure;
        }
    }

    public boolean move(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Cell[] cellWAY = new Cell[8];
        int index = 0;
        boolean expected = false;
        for (int i = 0; i < figures.length; i++) {
            if (figures[i] != null && figures[i].hashCode() == source.hashCode()) {
                index = i;                                            //запомним нужный  индекс элемента чтобы не бегать по массиву
                expected = true;
                cellWAY = figures[i].way(source, dest);
                break;
            } else {
                expected = false;
            }
        }
        //проверяем нет ли на пути фигур
        if (expected) {
            for (int i = 0; i < figures.length; i++) {
                if (cellWAY[1] == null) {
                    System.out.println("Вы не селали ход, пожалйста попробуйтее ещё");
                    break;
                }
                for (int j = 1; j < cellWAY.length; j++) {   //начём со второго элемента, т.к. первый координаты первоо элемента присутствуют в нашем массиве
                    if (figures[i] != null && cellWAY[j] != null) {
                        if (figures[i].hashCode() != cellWAY[j].hashCode()) {
                            expected = true;
                        } else if (figures[i].hashCode() == cellWAY[j].hashCode()) {
                            expected = false;
                        }
                    } else {
                        break;
                    }
                }
            }
            //если всё успешно то пересоздаём фигуру
            if (expected) {
                figures[index] = figures[index].figureCopy(dest);
                return expected;
            } else {
                throw new OccupiedWayException();
            }
        } else {
            throw new FigureNotFoundException();
        }
    }

    public Figure[] getFigures() {
        return figures;
    }
}