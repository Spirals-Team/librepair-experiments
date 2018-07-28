package ru.job4j.game.entities;

import ru.job4j.game.Board;
import ru.job4j.game.Cell;
import ru.job4j.game.Step;

import java.util.Random;

/**
 * @author Yury Matskevich
 */
public class Monster extends Personage {
	private final Random random = new Random();

	public Monster(Board board, Cell cell, long maxWaitTime) {
		super(board, cell, maxWaitTime);
	}

	/**
	 * @return one of the item from an enum {@link Step}
	 * which defins direction of moving
	 * @see Step
	 */
	private int getDirection() {
		return random.nextInt(Step.values().length);
	}

	@Override
	public synchronized Step getStep() {
		return Step.values()[getDirection()];
	}

	// A monster is a personage which move automatically
	@Override
	public synchronized void addToList(Step step) {
		throw new UnsupportedOperationException();
	}
}
