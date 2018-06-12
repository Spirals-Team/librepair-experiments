package ru.job4j.wait;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class OptimisticException extends Exception {
	public OptimisticException(String msg) {
		super(msg);
	}
}
