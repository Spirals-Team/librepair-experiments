package ru.job4j.game.entities;

import ru.job4j.game.Board;
import ru.job4j.game.Cell;
import ru.job4j.game.Step;

/**
 * @author Yury Matskevich
 */
public class Hero extends Personage {
	public Hero(Board board, Cell cell, long maxWaitTime) {
		super(board, cell, maxWaitTime);
	}

	//to get a next step from the step's store which is stored by users
	@Override
	public synchronized Step getStep() throws InterruptedException {
		while (!hasStepForMoving()) {
			wait();
		}
		return getStepForMoving();
	}
}
