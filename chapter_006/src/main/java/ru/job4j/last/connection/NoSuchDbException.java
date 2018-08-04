package ru.job4j.last.connection;

/**
 * If there is no settings for a current db
 * @author Yury Matskevich
 */
public class NoSuchDbException extends Exception {
	public NoSuchDbException(String msg) {
		super(msg);
	}
}
