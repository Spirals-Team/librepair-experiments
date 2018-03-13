package ru.job4j.chess;

public class FigureElephant extends Figure implements Cloneable {

    FigureElephant(Cell position) {
        super(position);
    }

    @Override
    Cell[] way(Cell dist) throws ImposibleMoveException {

        Cell[] result;

        if (Math.abs(dist.x - position.x) != Math.abs(dist.y - position.y)) {
            throw new ImposibleMoveException("The elephant walks diagonally!");
        } else {
            int numberOfCells = Math.abs(position.x - dist.x) + 1;
            int factor = 0;
            if (dist.x - position.x < 0) {
                factor = -1;
            } else {
                factor = 1;
            }
            Cell[] arrayOfCellsToGoThroug = new Cell[numberOfCells - 1];
            for (int i = 0; i < numberOfCells - 1; i++) {
                int k = i + 1;
                Cell cellToGoThroug = new Cell(position.x + k * factor, position.y + k * factor);
                arrayOfCellsToGoThroug[i] = cellToGoThroug;
            }

            result = arrayOfCellsToGoThroug;
        }
        return result;
    }

    @Override
    FigureElephant clone(Cell dist) {
        return new FigureElephant(dist);
    }
}