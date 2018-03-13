package ru.job4j.chess;

//5. Создать класс Board.
//        6. В классе создать массив Figure[] figures - содержит фигуры.
//        7. Добавить метод boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException
//        8. Метод должен должен проверить
//        - Что в заданной ячейки есть фигура. если нет. то выкинуть исключение
//        - Если фигура есть. Проверить может ли она так двигаться. Если нет то упадет исключение
//        - Проверить что полученный путь. не занят фигурами. Если занят выкинуть исключение
//        - Если все отлично. Записать в ячейку новое новое положение Figure figure.clone(Cell dist)
//        9. Изначально сделайте. только движения фигуры слон и покажите промежуточный результат.
public class Board {

    int sizeOfTheBoard;
    int numberOfFigures;
    int numberOfFiguresJustNow;

    Cell[] cells;

    Figure[] figures;

    Board(int size) {
        this.sizeOfTheBoard = size;
        this.cells = new Cell[this.sizeOfTheBoard * this.sizeOfTheBoard];
        int j = 0;
        for (int i = 1; i < this.sizeOfTheBoard + 1; i++) {
            for (int k = 1; k < this.sizeOfTheBoard + 1; k++) {
                this.cells[j] = new Cell(i, k);
                j++;

            }
        }
        this.numberOfFigures = size * size / 2;
        figures = new Figure[this.numberOfFigures];
        this.numberOfFiguresJustNow = 0;
    }

//    8. Метод должен должен проверить
//   - Что в заданной ячейки есть фигура. если нет. то выкинуть исключение
//   - Если фигура есть. Проверить может ли она так двигаться. Если нет то упадет исключение
//   - Проверить что полученный путь. не занят фигурами. Если занят выкинуть исключение
//   - Если все отлично. Записать в ячейку новое новое положение Figure figure.clone(Cell dist)
//9. Изначально сделайте. только движения фигуры слон и покажите промежуточный результат.
    boolean move(Cell source, Cell dist) throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {

        Figure figure = getFigureFromCell(source);

        if (figure == null)  {
            throw new FigureNotFoundException("In this cell there is no figure!");
        }

        Cell[] wayOfTheFigure = figure.way(dist);

        for (Cell cellOfWay : wayOfTheFigure) {
            if (getFigureFromCell(cellOfWay) != null) {
                throw new OccupiedWayException("This way is occupied");
            }
        }

        Figure clonedFigure = figure.clone(dist);

        updateTheFigereInTheArray(figure, clonedFigure);

        return true;
    }

    //Figure figure.clone(Cell dist) вместо этого удалить из массива фигур старую фигуру и добавить новую
    private void updateTheFigereInTheArray(Figure oldFigure, Figure newFigure) {

        Cell cellOfTheOldFigure = oldFigure.position;

        for (int ind = 0; ind < figures.length; ind++) {
            if (figures[ind] != null && cellOfTheOldFigure.equals(figures[ind].position)) {
                figures[ind] = newFigure;
            }
        }

    }

    Figure getFigureFromCell(Cell cell) {
        for (int ind = 0; ind < figures.length; ind++) {
            if (figures[ind] != null && cell.equals(figures[ind].position)) {
                return figures[ind];
            }
        }
        return null;
    }

    boolean inThisCellThereIsAFigure(Cell cell) {
        boolean inThisCellThereIsAFigure = false;
        for (int ind = 0; ind < figures.length; ind++) {
            if (cell.equals(figures[ind].position)) {
                inThisCellThereIsAFigure = true;
                break;
            }
        }
        return true;
    }

    boolean isTheCellIsOnTheBoard(Cell cell) {
        boolean cellIsOnTheBoard = true;

        if (cell.x > sizeOfTheBoard || cell.y > sizeOfTheBoard) {
            cellIsOnTheBoard = false;
        }
        return cellIsOnTheBoard;
    }

    void addFigureToTheArrayOfFigures(Figure figure) {
        figures[numberOfFiguresJustNow] = figure;
        numberOfFiguresJustNow = numberOfFiguresJustNow + 1;
    }
}

class Cell {

    int x;
    int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        Cell cell = (Cell) obj;

        return cell.x == this.x && cell.y == this.y;
    }

    @Override
    public int hashCode() {
        return this.x * this.y;
    }
}