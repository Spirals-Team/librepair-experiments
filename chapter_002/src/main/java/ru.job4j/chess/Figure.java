package ru.job4j.chess;

//1. Прочитать про шаблон проектирования стратегия.
//        2. Создать aбстрактный класс Figure
//        2. В Figure сделать поля final Cell position - и конструктор. В классе не
//        должно быть методов set get
//        3. Добавить в класс Figure абстрактный метод Cell[] way(Cell dist)
//        throw ImposibleMoveException
//        Метод должен работать так. dist - задают ячейку куда следует пойти.
//        Если фигура может туда пойти. то Вернуть массив ячеек. которые должна
//        пройти фигура.
//        Если фигура туда пойти не может. выбросить исключение ImposibleMoveException
//        4. Cell - описывает ячейки шахматной доски. Содержит координаты x, y. Cell
//        не может содержать объект Figure.
public abstract class Figure {

    final Cell position;

    protected Figure(Cell position) {
        this.position = position;
    }

    abstract Cell[] way(Cell dist) throws ImposibleMoveException;

    abstract Figure clone(Cell dist);
}