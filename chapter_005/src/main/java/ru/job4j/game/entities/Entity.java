package ru.job4j.game.entities;

import ru.job4j.game.Board;
import ru.job4j.game.Cell;

/**
 * @author Yury Matskevich
 */
public abstract class Entity implements Runnable {
	private final Board board;
	private final Cell cell;

	public Entity(Board board, Cell cell) {
		this.board = board;
		this.cell = cell;
	}

	public Board getBoard() {
		return board;
	}

	public Cell curPosition() {
		return cell;
	}

	@Override
	public void run() {
		//to block the start cell
		board.getCellForLocking(cell).lock();
	}
}
