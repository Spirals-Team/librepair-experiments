package ru.job4j.tracker;

import ru.job4j.tracker.dao.ITracker;
import ru.job4j.tracker.dao.TrackerList;

/**
 * @author Yury Matskevich
 */
public class StartUITestList extends StartUITest {
	@Override
	public ITracker getTracker() {
		return new TrackerList();
	}
}
