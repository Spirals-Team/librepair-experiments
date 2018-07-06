package ru.job4j.game;

import java.util.Objects;

/**
 * @author Yury Matskevich
 */
public class Cell {
	private int x;
	private int y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * To get a new cell from the cell increment
	 * @param step a cell increment
	 * @return a new cell which was made from previous cell and the step
	 */
	public Cell cellAfterStep(Step step) {
		Cell result;
		switch (step) {
			case down:
				result = new Cell(getX(), getY() + 1);
				break;
			case up:
				result = new Cell(getX(), getY() - 1);
				break;
			case left:
				result = new Cell(getX() - 1, getY());
				break;
			default:
				result = new Cell(getX() + 1, getY());
				break;
		}
		return result;
	}

	/**
	 * To get {@link Step} from a cell increment
	 * @param dist a cell of desination
	 * @return Step
	 */
	public Step getStepFromNextCell(Cell dist) {
		int deltaX = dist.getX() - getX();
		int deltaY = dist.getY() - getY();
		Step step = null;
		if (deltaX == 0 & deltaY == 1) {
			step = Step.down;
		}
		if (deltaX == 0 & deltaY == -1) {
			step = Step.up;
		}
		if (deltaX == -1 & deltaY == 0) {
			step = Step.left;
		}
		if (deltaX == 1 & deltaY == 0) {
			step = Step.right;
		}
		return step;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Cell cell = (Cell) o;
		return x == cell.getX()
				&& y == cell.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
