package ru.aserdyuchenko.start;

import ru.aserdyuchenko.models.Figure;

/**
 * This class for creat base figure. This for class menuFigure, where we creat all figures.
 */

public abstract class BaseFigure implements UserFigure {
	String nameFigure;
	public BaseFigure(String nameFigure) {
		this.nameFigure = nameFigure;
	}

	public abstract int key();
	public abstract void check(Figure figure, int newLocationBoardX, int newLocationBoardY, Chess chess);

	public String info() {
		return String.format("%s. %s", this.key(), this.nameFigure);
	}
}
