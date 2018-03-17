package ru.job4j.testtask.figures;

import ru.job4j.testtask.Cell;
import ru.job4j.testtask.Figure;
import ru.job4j.testtask.exceptions.ImpossibleMoveException;

import java.util.Arrays;

/**
 * Elephant.
 * @author Hincu Andrei (andreih1981@gmail.com) by 11.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Elephant extends Figure {
    /**
     * Конструктор.
     * @param name     название фигуры.
     * @param pozition позиция фигуры.
     */
    public Elephant(String name, Cell pozition) {
        super(name, pozition);
    }

    /**
     * медод хождения слона.
     * @param dist точка куда хотели бы пойти.
     * @return массив ходов.
     * @throws ImpossibleMoveException ерор.
     */
    @Override
    public Cell[] way(Cell dist) throws ImpossibleMoveException {

       Cell currentCell = null;
       boolean found = false;
       for (int i = 1; i != 9; i++) {
           if (found) {
               break;
           }
           for (int j = 1; j != 9; j++) {
              Cell cell = new Cell(i, j);
               if (this.isOneCell(cell)) {
                   currentCell = cell;
                   found = true;
                   break;
               }
           }
       }
        if (Math.abs(dist.getHeight() - currentCell.getHeight()) != Math.abs(dist.getWidth() - currentCell.getWidth())) {
           throw new ImpossibleMoveException("this elephant can not walk like this");
        }
       Cell[] away = new Cell[8];
       int pozition = 0;
        if (dist.getWidth() > currentCell.getWidth() && dist.getHeight() > currentCell.getHeight()) {
            int j = currentCell.getWidth() + 1;
            for (int i = currentCell.getHeight() + 1; i < dist.getHeight() + 1; i++) {
                away[pozition] = new Cell(i, j);
                pozition++;
                j++;
            }
        } else if (dist.getWidth() < currentCell.getWidth() && dist.getHeight() < currentCell.getHeight()) {
            int j = currentCell.getWidth() - 1;
            for (int i = currentCell.getHeight() - 1; i > dist.getHeight() - 1; i--) {
              away[pozition] = new Cell(j, i);
              j--;
              pozition++;
          }
        } else if (dist.getWidth() < currentCell.getWidth() && dist.getHeight() > currentCell.getHeight()) {
            int j = currentCell.getWidth() - 1;
            for (int i = currentCell.getHeight() + 1; i < dist.getHeight() + 1; i++) {
                away[pozition] = new Cell(j, i);
                pozition++;
                j--;
            }
        } else if (dist.getWidth() > currentCell.getWidth() && dist.getHeight() < currentCell.getHeight()) {
            int j = currentCell.getWidth() + 1;
            for (int i = currentCell.getHeight() - 1; i > dist.getHeight() - 1; i--) {
                away[pozition] = new Cell(j, i);
                pozition++;
                j++;
            }
        }
        if (away[0] == null) {
            throw new ImpossibleMoveException("impossible move.");
        }
        int count = 0;
        for (Cell cell : away) {
            if (cell != null) {
                count++;
            }
        }
        return Arrays.copyOf(away, count);
    }

}
