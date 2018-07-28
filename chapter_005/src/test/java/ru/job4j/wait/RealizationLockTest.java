package ru.job4j.wait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class RealizationLockTest {
	private PrintStream stout = System.out;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		System.setOut(new PrintStream(out));
	}

	@After
	public void setBack() {
		System.setOut(stout);
	}

	private class Factorial {
		private int result;
		private ILock lock;

		public Factorial(ILock lock) {
			this.lock = lock;
		}

		private int fact(int n) {
			if (n == 1) {
				return 1;
			}
			result = fact(n - 1) * n;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return result;
		}

		public void getFactorial(int value) {
			lock.lock();
			System.out.println(fact(value));
			lock.unlock();
		}
	}

	@Test
	public void factorialTest() throws InterruptedException {
		ILock lock = new RealizationLock();
		Factorial factorial = new Factorial(lock);

		Thread thread1 = new Thread() {
			@Override
			public void run() {
				lock.unlock();
				factorial.getFactorial(10);
			}
		};
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				lock.unlock();
				factorial.getFactorial(5);
			}
		};
		Thread thread3 = new Thread() {
			@Override
			public void run() {
				lock.unlock();
				factorial.getFactorial(6);
			}
		};
		thread1.start();
		thread2.start();
		thread3.start();
		thread1.join();
		thread2.join();
		thread3.join();
		Set<String> resultSet = new HashSet<>(
				Arrays.asList(
						new String(out.toByteArray())
								.split(System.lineSeparator())
				)
		);
		Set<String> expectedSet = new HashSet<>(
				Arrays.asList("3628800", "120", "720")
		);
		assertEquals(expectedSet, resultSet);
	}
}