package ru.job4j.game;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yury Matskevich
 */
public class LockBoard {
	private final ReentrantLock[][] board;

	public LockBoard(int xSize, int ySize) {
		this.board = new ReentrantLock[ySize][xSize];
		initialLock();
	}

	//just for initialization of array of ReentrantLock[][]
	private void initialLock() {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				board[y][x] = new ReentrantLock();
			}
		}
	}

	/**
	 * To get {@link ReentrantLock} of a selected cell
	 * @param cell cell of the board
	 * @return {@link ReentrantLock} of the current cell
	 */
	public ReentrantLock getLock(Cell cell) {
		return board[cell.getY()][cell.getX()];
	}

	/**
	 * To make sure that a cell is in scope of the game board
	 * @param cell a cell for testing
	 * @return true - if the cell in scope of the board, else false
	 */
	public boolean isOnBoard(Cell cell) {
		int x = cell.getX();
		int y = cell.getY();
		return y >= 0
				&& y < board.length
				&& x >= 0
				&& x < board[0].length;
	}
}
