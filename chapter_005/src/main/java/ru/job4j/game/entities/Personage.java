package ru.job4j.game.entities;

import ru.job4j.game.Board;
import ru.job4j.game.Cell;
import ru.job4j.game.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Yury Matskevich
 */
public abstract class Personage extends Entity {
	private final List<Cell> stepHist = new ArrayList<>();
	private final long maxWaitTime; //how much is thread waiting for the lock?
	private final List<Step> nextStep = new ArrayList<>(); //a next step for moving

	Personage(Board board, Cell cell, long maxWaitTime) {
		super(board, cell);
		this.maxWaitTime = maxWaitTime;
		stepHist.add(cell);
	}

	/**
	 * Method for getting array of position of character on the game board
	 * @return array of current position of character
	 * where array[0] is position along x and array[1]
	 * is position along y
	 */
	@Override
	public Cell curPosition() {
		return stepHist.get(stepHist.size() - 1);
	}

	/**
	 * Method for getting array of position of character on the game board
	 * one step back
	 * @return array of previous position of character
	 * where array[0] is position along x and array[1]
	 * is position along y
	 */
	private Cell prevPosition() {
		return stepHist.get(stepHist.size() - 2);
	}

	//Getting step
	public abstract Step getStep() throws InterruptedException;

	/**
	 * Adding a new personage's step to the step's store
	 * @param step a next personage's step
	 */
	public synchronized void addToList(Step step) {
		nextStep.add(step);
		notifyAll();
	}

	//To get step for moving
	Step getStepForMoving() {
		return nextStep.remove(0);
	}

	/**
	 * To make sure that there is a step in the step's store
	 * @return true - if there is a ne
	 */
	boolean hasStepForMoving() {
		return !nextStep.isEmpty();
	}

	/**
	 * Personage does a step
	 * @throws InterruptedException if the current thread is interrupted
	 */
	synchronized void move() throws InterruptedException {
		Board board = getBoard();
		Cell coord = curPosition(); //current position of personage
		Cell newCell;
		do {
			newCell = coord.cellAfterStep(getStep());
		} while (!board.isOnBoard(newCell)
				|| !board.getCellForLocking(newCell).tryLock(maxWaitTime, TimeUnit.MILLISECONDS));
		stepHist.add(newCell);
		System.out.format("x: %d, y: %d, %s%n", newCell.getX(), newCell.getY(), Thread.currentThread().getName()); //to show steps
		coord = prevPosition(); //previous position
		board.getCellForLocking(coord).unlock(); //unlock previous position
	}

	/**
	 * Starting new thread. Personage does a step each a second
	 * if there is a step in the step's store
	 */
	@Override
	public void run() {
		super.run();
		try {
			while (!Thread.currentThread().isInterrupted()) {
				move();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// log
		}
	}
}
