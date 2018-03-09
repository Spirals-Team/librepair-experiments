package ru.aserdyuchenko.start;

import ru.aserdyuchenko.start.Input;
import ru.aserdyuchenko.start.Tracker;

public interface UserAction {

	abstract int key();

	abstract void execute(Input input, Tracker tracker);

	abstract String info();
}
