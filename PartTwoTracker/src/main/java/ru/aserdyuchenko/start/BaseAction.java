package ru.aserdyuchenko.start;

public abstract class BaseAction implements UserAction {
	String nameAction;
	public BaseAction(String nameAction) {
		this.nameAction = nameAction;
	}

	public abstract int key();

	public abstract void execute(Input input, Tracker tracker);

	public String info() {
		return String.format("%s. %s", this.key(), this.nameAction);
	}
}
