package ru.job4j.game.entities;

import ru.job4j.game.Board;
import ru.job4j.game.Cell;

/**
 * This entity block a cell of game board during all the game
 * @author Yury Matskevich
 */
public class Rock extends Entity {
	public Rock(Board board, Cell cell) {
		super(board, cell);
	}
}
