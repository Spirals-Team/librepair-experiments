package ru.aserdyuchenko.tracker.start;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import ru.aserdyuchenko.tracker.models.Task;
import ru.aserdyuchenko.tracker.models.Item;

public class TrackerTest {
	@Test
    public void whenAddTwoNewItemAndСheckSecondItem() {
			Tracker tracker = new Tracker();
			tracker.add(new Task("first task", "first desc"));
			tracker.add(new Task("second task", "second desc"));
			Item item = tracker.findByName("second task");
			String result = item.getName();
			assertThat(result, is("second task"));
    }
	@Test
    public void whenRemovalFirstItem() {
			Tracker tracker = new Tracker();
			tracker.add(new Task("first task", "first desc"));
			tracker.add(new Task("second task", "second desc"));
			String itemId = tracker.findIdByName("first task");
			tracker.delete(itemId);
			assertNull(tracker.findById(itemId));
    }
	@Test
    public void whenEditingFirstDescription() {
			Tracker tracker = new Tracker();
			tracker.add(new Task("first task", "first desc"));
			tracker.add(new Task("second task", "second desc"));
			String itemId = tracker.findIdByName("first task");
			tracker.updateItem(itemId, new Task("first task", "first description"));
			Item item = tracker.findByName("first task");
			String result = item.getDescription();
			assertThat(result, is("first description"));
    }
	@Test
    public void whenFindItemByName() {
			Tracker tracker = new Tracker();
			tracker.add(new Task("first task", "first desc"));
			tracker.add(new Task("second task", "second desc"));
			Item item = tracker.findByName("first task");
			String result = item.getName();
			assertThat(result, is("first task"));
    }
}
