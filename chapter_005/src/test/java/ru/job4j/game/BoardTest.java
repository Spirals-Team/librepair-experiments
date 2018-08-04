package ru.job4j.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 */
public class BoardTest {
	private PrintStream stdout = System.out;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private Board board;

	@Before
	public void loadOutput() {
		System.setOut(new PrintStream(out));
	}

	@After
	public void backOut() {
		System.setOut(stdout);
	}

	private void setUp() {
		int xSize = 3;
		int ySize = 3;
		board = new Board(xSize, ySize);
	}

	//to get number from string in current position
	private int getIntegerFromString(String str, int position) {
		return Integer.parseInt(Character.toString(str.charAt(position)));
	}

	@Test
	public void whenFailStepThenMethodMoveReturnsFalse() {
		setUp();
		board.addEntity("hero", new Cell(0, 0));
		board.addEntity("hero", new Cell(2, 0));
		board.addEntity("hero", new Cell(2, 2));
		board.addEntity("hero", new Cell(0, 2));
		board.startGame();
		//fail ssteps for first hero
		assertFalse(board.move(new Cell(0, 0), new Cell(-1, 0)));
		assertFalse(board.move(new Cell(0, 0), new Cell(0, -1)));
		//fail ssteps for second hero
		assertFalse(board.move(new Cell(0, 0), new Cell(3, 0)));
		assertFalse(board.move(new Cell(0, 0), new Cell(0, -1)));
		//fail ssteps for third hero
		assertFalse(board.move(new Cell(0, 0), new Cell(3, 2)));
		assertFalse(board.move(new Cell(0, 0), new Cell(2, 3)));
		//fail ssteps for fourth hero
		assertFalse(board.move(new Cell(0, 0), new Cell(-1, 2)));
		assertFalse(board.move(new Cell(0, 0), new Cell(0, 3)));
		//moving from cells which are not esist
		assertFalse(board.move(new Cell(-1, 1), new Cell(0, 1)));
		assertFalse(board.move(new Cell(1, -1), new Cell(1, 0)));
		assertFalse(board.move(new Cell(3, 1), new Cell(2, 1)));
		assertFalse(board.move(new Cell(1, 3), new Cell(1, 2)));
	}


	@Ignore
	@Test
	public void test() throws InterruptedException {
		setUp();
		board.addEntity("hero", new Cell(0, 0));
		board.startGame();
		assertTrue(board.move(new Cell(0, 0), new Cell(0, 1)));
		Thread.sleep(1000);
		assertTrue(board.move(new Cell(0, 1), new Cell(1, 1)));
		Thread.sleep(1000);
		assertTrue(board.move(new Cell(1, 1), new Cell(1, 0)));
		Thread.sleep(1000);
		assertTrue(board.move(new Cell(1, 0), new Cell(0, 0)));
		Thread.sleep(1000);
		board.stopGame();

		//check steps of hero
		List<Cell> actualList = new ArrayList<>();
		String[] strings = new String(out.toByteArray()).split(System.lineSeparator());
		System.out.println(strings.length);
		for (String item : strings) {
			int x = getIntegerFromString(item, 3);
			int y = getIntegerFromString(item, 9);
			actualList.add(new Cell(x, y));
		}
		assertEquals(
				Arrays.asList(
						new Cell(0, 1),
						new Cell(1, 1),
						new Cell(1, 0),
						new Cell(0, 0)),
				actualList
		);
	}

	@Ignore
	@Test
	public void characterIsInScopeOfGameBoard() throws InterruptedException {
		setUp();
		board.addEntity("monster", new Cell(0, 0));
		board.addEntity("monster", new Cell(1, 1));
		board.addEntity("monster", new Cell(2, 2));
		board.startGame();
		Thread.sleep(10000);
		board.stopGame();

		//all the steps have to be in scope of a game board
		String[] strings = new String(out.toByteArray()).split(System.lineSeparator());
		for (String item : strings) {
			int x = getIntegerFromString(item, 3);
			int y = getIntegerFromString(item, 9);
			assertTrue("Step isn't in scope of board", board.isOnBoard(new Cell(x, y)));
		}
	}

	@Test
	public void characterIsInScopeOfGameBoard1() throws InterruptedException {
		setUp();
		board.addEntity("rock", new Cell(1, 1));
		board.addEntity("hero", new Cell(0, 0));
		board.startGame();
		assertTrue(board.move(new Cell(0, 0), new Cell(0, 1)));
		Thread.sleep(1000);
		//try to stay on the rock's cell
		assertFalse(board.move(new Cell(0, 1), new Cell(1, 1)));
		Thread.sleep(1000);
		board.stopGame();

		//all the steps have to be in scope of a game board
		List<Cell> actualList = new ArrayList<>();
		String[] strings = new String(out.toByteArray()).split(System.lineSeparator());
		System.out.println(strings.length);
		for (String item : strings) {
			int x = getIntegerFromString(item, 3);
			int y = getIntegerFromString(item, 9);
			actualList.add(new Cell(x, y));
		}
		assertEquals(Arrays.asList(new Cell(0, 1)), actualList);
	}
}