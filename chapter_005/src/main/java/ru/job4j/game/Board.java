package ru.job4j.game;

import ru.job4j.game.entities.Entity;
import ru.job4j.game.entities.Personage;
import ru.job4j.game.entities.Rock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yury Matskevich
 */
public class Board {
	private final EntityFactory entityFactory;
	private final List<Thread> threads = new ArrayList<>(); //A store for threads
//	private final List<Personage> personages = new ArrayList<>(); //A store for personages
//	private final List<Rock> rocks = new ArrayList<>(); //A store for rocks
	private final List<Entity> entities = new ArrayList<>();
	private final LockBoard lockBoard;

	public Board(int xSize, int ySize) {
		lockBoard = new LockBoard(xSize, ySize);
		entityFactory =  new EntityFactory(this);
	}

	/**
	 * To add a new entity on the game board
	 * @param name a name of the personage
	 * @param cell the initial position of the entity on the game board
	 */
	public void addEntity(String name, Cell cell) {
		Entity cur = entityFactory.createEttity(name, cell);
		threads.add(new Thread(cur));
		entities.add(cur);
	}

	/**
	 * Starting of the game
	 */
	public void startGame() {
		for (Thread unit : threads) {
			unit.start();
		}
	}

	/**
	 * Stopping of the game
	 */
	public void stopGame() {
		for (Thread unit : threads) {
			unit.interrupt();
		}
	}

	/**
	 * To move the {@link Personage} from one cell to another
	 * @param source a cell where is a {@link Personage}
	 * @param dist a cell of desination
	 * @return true if there is a {@link Personage} in a source cell and it can move to the dist cell,
	 * false if else
	 */
	public boolean move(Cell source, Cell dist) {
		boolean result = false;
		if (!isOnBoard(source) || !isOnBoard(dist)) {
			return false;
		}
		for (Entity unit : entities) {
			if (unit instanceof Rock && unit.curPosition().equals(dist)) {
				return false;
			}
			if (unit instanceof Personage && unit.curPosition().equals(source)) {
				Personage cur = (Personage) unit;
				cur.addToList(source.getStepFromNextCell(dist));
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * To make sure that cell is in scope of the game board
	 * @param cell a cell for testing
	 * @return true - if the cell in scope of the game board, else false
	 */
	public boolean isOnBoard(Cell cell) {
		return lockBoard.isOnBoard(cell);
	}

	/**
	 * To get reference of {@link ReentrantLock} object for the current cell of the game board
	 * @param cell a cell for getting a reference of {@link ReentrantLock} object
	 * @return a reference of {@link ReentrantLock} object which related to the current cell
	 */
	public ReentrantLock getCellForLocking(Cell cell) {
		return lockBoard.getLock(cell);
	}
}
