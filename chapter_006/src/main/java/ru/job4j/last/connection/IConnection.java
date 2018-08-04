package ru.job4j.last.connection;

import java.sql.Connection;

/**
 * @author Yury Matskevich
 */
public interface IConnection {
	/**
	 * Gets connection of different databases
	 * @return {@link Connection} of current db
	 */
	Connection connect();
}
