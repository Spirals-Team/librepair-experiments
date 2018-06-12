package ru.job4j.game;

import ru.job4j.game.entities.Entity;
import ru.job4j.game.entities.Hero;
import ru.job4j.game.entities.Monster;
import ru.job4j.game.entities.Rock;

/**
 * @author Yury Matskevich
 */
public class EntityFactory {
	private final Board board;

	public EntityFactory(Board board) {
		this.board = board;
	}

	/**
	 * To create a new entity for adding on the game board
	 * @param name name of the entity
	 * @param cell the starting cell on the board
	 * @return An entity depending on the parameters
	 */
	public Entity createEttity(String name, Cell cell) {
		Entity character = null;
		if (name.equals("hero")) {
			character = new Hero(board, cell, 500);
		}
		if (name.equals("monster")) {
			character = new Monster(board, cell, 5000);
		}
		if (name.equals("rock")) {
			character = new Rock(board, cell);
		}
		return character;
	}
}
