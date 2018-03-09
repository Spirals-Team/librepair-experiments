package ru.aserdyuchenko.start;

import ru.aserdyuchenko.start.Chess;
import ru.aserdyuchenko.models.Figure;

public interface UserFigure {
	abstract int key();
	abstract void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess);
	abstract String info();
}
