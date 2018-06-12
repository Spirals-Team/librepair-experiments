package ru.job4j.jdbc.tracker.connect;

import java.sql.Connection;

/**
 * @author Yury Matskevich
 */
public interface IConnection {
	Connection connect();
}
