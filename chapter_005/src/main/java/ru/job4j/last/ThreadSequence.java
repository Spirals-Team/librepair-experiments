package ru.job4j.last;

/**
 * @author Yury Matskevich
 */
public class ThreadSequence implements Runnable {
	private final Store store;
	private final int number;
	private int sequence;

	/**
	 * Creates new Runnable object which will add a sequence of number to
	 * the store.
	 * @param store where the numbers will be added.
	 * @param number a number which will be added to the store by thread.
	 * @param sequence how many nubmers will be added to the store by
	 * current thread.
	 */
	public ThreadSequence(Store store, int number, int sequence) {
		this.store = store;
		this.number = number;
		this.sequence = sequence;
	}

	@Override
	public void run() {
		while (true) {
			store.fill(number, sequence);
		}
	}
}
