package ru.job4j.chessBoard;

public class Board {
    Figure[][] figures = new Figure[8][8];

    public void add(Figure figure) {
        this.figures[figure.position.x][figure.position.y] = figure;
    }

    boolean move(Figure source, Cell dest) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        if (source == null) {
            throw new FigureNotFoundException("Figure not found!");
        }
        Cell[] way = source.way(source.position, dest);
        for (int i = 0; i < way.length; i++) {
            System.out.println(way[i].y);
            if (way[i] == null)
                throw new OccupiedWayException("OccupiedWayException!");
        }
        return true;
    }
}

