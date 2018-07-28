package ru.job4j.tracker;

import ru.job4j.tracker.connect.PostgreDb;
import ru.job4j.tracker.dao.TrackerDb;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.start.StartUI;

/**
 * @author Yury Matskevich
 */
public class StartDb {
	public static void main(String[] args) {
		new StartUI(
				new ValidateInput(new ConsoleInput()),
				new TrackerDb(new PostgreDb("/config.properties"))
		).init();
	}
}
