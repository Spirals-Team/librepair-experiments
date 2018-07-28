package ru.job4j.last;

/**
 * This class guaranty that dead block will appear
 * when first thread will be doing method called first()
 * and other thread will be doing method called second().
 * @author Yury Matskevich
 */
public class GuarantyDeadLock {
	private final Object lock1 = new Object();
	private final Object lock2 = new Object();
	private volatile boolean flag1 = false;
	private volatile boolean flag2 = false;

	public void first() {
		synchronized (lock1) {
			flag1 = true;
			while (!flag2) {
				int i; //just for checkstyle
				/*this "barrier" will be broken, when
				monitor of lock2 will be taken by thread*/
			}
			synchronized (lock2) {
				System.out.println("We'll never see this text");
			}
		}
	}

	public void second() {
		synchronized (lock2) {
			flag2 = true;
			while (!flag1) {
				int i; //just for checkstyle
				/*this "barrier" will be broken, when
				monitor of lock1 will be taken by thread*/
			}
			synchronized (lock1) {
				System.out.println("We'll never see this text");
			}
		}
	}
}
