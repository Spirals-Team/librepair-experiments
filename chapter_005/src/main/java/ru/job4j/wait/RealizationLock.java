package ru.job4j.wait;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class RealizationLock implements ILock {
	private Map<Thread, Boolean> status = new HashMap<>();
	private boolean statusLock = false;

	@Override
	public synchronized void lock() {
		Thread cur = Thread.currentThread();
		while (statusLock && !status.getOrDefault(cur, false)) {
			status.putIfAbsent(cur, false);
			try {
				wait();
			} catch (InterruptedException e) {
				// log
			}
		}
		statusLock = true;
		status.put(cur, true);
	}

	@Override
	public synchronized void unlock() {
		Thread cur = Thread.currentThread();
		if (status.getOrDefault(cur, false)) {
			statusLock = false;
			notify();
		}
	}
}
