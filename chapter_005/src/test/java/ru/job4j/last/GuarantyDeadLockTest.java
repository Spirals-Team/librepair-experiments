package ru.job4j.last;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.model.TestTimedOutException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 */
public class GuarantyDeadLockTest {
	private final PrintStream sdout = System.out;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		System.setOut(new PrintStream(out));
	}

	@After
	public void backDown() {
		System.setOut(sdout);
	}

	@Test()
	public void whenDeadLockAppearsThen() throws InterruptedException {
		GuarantyDeadLock deadLock = new GuarantyDeadLock();
		Thread thread1 = new Thread(() -> deadLock.first());
		Thread thread2 = new Thread(() -> deadLock.second());
		thread1.start();
		thread2.start();
		thread1.join(10000);
		thread2.join(10000);
		assertEquals(
				"there will not be any text in the console because of deadlock",
				new String(out.toByteArray()),
				""
		);
	}
}