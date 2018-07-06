package ru.job4j.tracker.connect;

import java.sql.Connection;

/**
 * @author Yury Matskevich
 */
public interface IConnection {
	Connection connect();
}
