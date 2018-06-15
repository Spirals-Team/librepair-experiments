package ru.job4j.chess;

/**
 * Класс фигуры слон
 * @author Daniil Emelyanov
 * @since 17.04.2018
 * @version 0.1
 */
public class Bishop extends Figure {
    /**
     * Конструктр класса Bishop, вызывается из класса Figure
     * @param dest ячейка с новыми координатами
     */
     Bishop(Cell dest) {
        super(dest);
    }

    /**
     * Метод хода фигуры Bishop.
     * @param source ячейка начального положения фигуры.
     * @param dest ячейка для хода фигуры.
     * @return Если фигура может пойти в dest возврощает массив пройденных ячеек.
     * @throws ImposableMoveException выводим ошибку если фигура не может пройти такой путь
     */

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImposableMoveException {
        // счетчик индекса пути фигуры
        int count = 1;
        int size = source.getX() + 1;
        // путь фигуры
        Cell[] currentCourse = new Cell[size];
        // координаты по x и по y должны быть равны по модулю, так как слол ходит только по диагонали
        if (Math.abs(dest.getX() - source.getX()) != Math.abs(dest.getY() - source.getY())) {
            throw new ImposableMoveException("Movement can not be made");
        }
        // Проверяем, что разница между начальной и конечной координатой меньше либо равна -1, так как
        // слон ходит только по диагонали
            if ((source.getX() - dest.getX()) <= -1 && (source.getY() - dest.getY()) <= -1) {
                // проходим в цикле логику пути фигуры, где начальным точккам X и Y на первой итерации прибавляем  один шаг
                // затем инкрементируем
                for (int i = source.getX() + 1, j = source.getY() + 1; count <= Math.abs(dest.getX() - source.getX()); i++, j++) {
                    // Заполняем массив  ячеек координатами пройденных ячеек
                    currentCourse[count - 1] = new Cell(i, j);
                    count++;
                }
            }
        return currentCourse;
    }
    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest);
    }
}