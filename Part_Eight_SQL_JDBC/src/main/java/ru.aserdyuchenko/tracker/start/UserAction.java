package ru.aserdyuchenko.tracker.start;

import java.sql.SQLException;

public interface UserAction {

	abstract int key();

	abstract void execute(Input input, Tracker tracker) throws SQLException;

	abstract String info();
}
