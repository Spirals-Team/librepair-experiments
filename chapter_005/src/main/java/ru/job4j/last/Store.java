package ru.job4j.last;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * The class is store of string. Threads can add numbers to the string
 * or set of numbers doing it alternating.
 * @author Yury Matskevich
 */
public class Store {
	private String str;

	/**
	 * Adds a new integer to the stored string
	 * @param number an integer which is going to be
	 * appended to the string.
	 */
	public synchronized void add(int number) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(str == null ? "" : str)
				.append(Integer.toString(number));
		str = strBuf.toString();
	}

	/**
	 * Adds specified quantity of numbers to the string.
	 * @param number a number for adding
	 * @param sequence a quantity of numbers for adding
	 */
	public synchronized void fill(int number, int sequence) {
		notify();
		int temp = sequence;
		while (temp != 0) {
			add(number);
			temp--;
		}
		try {
			wait();
		} catch (InterruptedException e) {
			//log
		}
	}

	/**
	 * Returns the stored string
	 * @return the stored string
	 */
	public synchronized String getStr() {
		return str;
	}
}
