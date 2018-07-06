package ru.job4j.last;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 */
public class StoreTest {
	@Test
	public void whenAddsIntegerThenThisOneIsBeingAppendToTheStoredString() {
		Store store = new Store();
		store.add(1);
		store.add(2);
		store.add(10);
		assertThat(
				"Object of Store has to have string wich"
						+ " include all the given integers wich have"
						+ " been passed via method of add",
				store.getStr(),
				is("1210")
		);
	}

	@Test
	public void whenLaunchTwoThreadThenGetStringOfSequenceOfSetOfNumbers() {
		Store store = new Store();
		Thread thread1 = new Thread(new ThreadSequence(store, 1, 10));
		Thread thread2 = new Thread(new ThreadSequence(store, 2, 10));
		thread1.start();
		thread2.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			//
		}
		char[] chars = store.getStr().toCharArray(); //get sequence of numbers
		char temp = 'z';
		for (int i = 0; i < chars.length; i++) {
			if (i % 10 == 0) {
				temp = chars[i];
			}
			assertEquals(temp, chars[i]);
		}
	}
}