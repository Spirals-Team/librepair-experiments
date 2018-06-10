package ru.job4j.tracker;

import java.util.Arrays;
import ru.job4j.models.*;
import org.junit.Test;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {
	@Test
	public void whenReplaceNameThenReturnNewName() {
		Tracker tracker = new Tracker();
		Item previous = new Item("test1", "testDescription", 123L);
		// Добавляем заявку в трекер. Теперь в объект проинициализирован id.
		tracker.add(previous);
		// Создаем новую заявку.
		Item next = new Item("test2", "testDescription2", 1234L);
		// Проставляем старый id из previous, который был сгенерирован выше.
		next.setId(previous.getId());
		// Обновляем заявку в трекере.
		tracker.replace(previous.getId(), next);
		// Проверяем, что заявка с таким id имеет новые имя test2.
	
		assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
	}
	
	@Test
	public void whenDeleteItemThenReturnName() {
		Tracker tracker = new Tracker();
		Item previous = new Item("test1", "testDescription", 123L);
		// Добавляем заявку в трекер. Теперь в объект проинициализирован id.
		tracker.add(previous);
		// Создаем новую заявку.
		Item next = new Item("test2", "testDescription2", 1234L);
		tracker.add(next);
		
		Item nextOne = new Item("test3", "testDescription3", 1235L);
		tracker.add(nextOne);

		tracker.delete(previous.getId());
		// Проверяем, что заявка с таким id имеет новые имя test2.
		// находит массив из двух элементов(превьас и нул)
		assertThat(tracker.findAll().size(), is(2));
	}
}	