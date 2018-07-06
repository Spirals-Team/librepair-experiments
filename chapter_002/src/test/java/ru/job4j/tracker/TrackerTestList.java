package ru.job4j.tracker;

import ru.job4j.tracker.dao.ITracker;
import ru.job4j.tracker.dao.TrackerList;

/**
 * @author Yury Matskevich
 */
public class TrackerTestList extends TrackerTest {
	@Override
	public ITracker getTracker() {
		return new TrackerList();
	}
}
