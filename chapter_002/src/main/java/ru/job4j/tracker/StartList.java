package ru.job4j.tracker;

import ru.job4j.tracker.dao.TrackerList;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.start.StartUI;

/**
 * @author Yury Matskevich
 */
public class StartList {
	public static void main(String[] args) {
		new StartUI(
				new ValidateInput(new ConsoleInput()),
				new TrackerList()
		).init();
	}
}
