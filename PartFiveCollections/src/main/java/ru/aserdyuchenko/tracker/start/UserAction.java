package ru.aserdyuchenko.tracker.start;

import ru.aserdyuchenko.tracker.start.Input;
import ru.aserdyuchenko.tracker.start.Tracker;

public interface UserAction {

	abstract int key();

	abstract void execute(Input input, Tracker tracker);

	abstract String info();
}
