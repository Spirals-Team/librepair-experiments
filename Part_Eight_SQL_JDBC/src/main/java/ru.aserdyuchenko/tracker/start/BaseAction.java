package ru.aserdyuchenko.tracker.start;

import java.sql.SQLException;

public abstract class BaseAction implements UserAction {
	String nameAction;
	public BaseAction(String nameAction) {
		this.nameAction = nameAction;
	}

	public abstract int key();

	public abstract void execute(Input input, Tracker tracker) throws SQLException;

	public String info() {
		return String.format("%s. %s", this.key(), this.nameAction);
	}
}
