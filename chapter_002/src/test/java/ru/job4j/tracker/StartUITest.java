package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import ru.job4j.models.*;
import org.junit.Test;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartUITest {
	private final Tracker tracker = new Tracker();
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	@Before
    public void loadItem() {
		System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
	}	
	
	@After
	public void backItem() {
		System.setOut(this.stdout);
        System.out.println("execute after method");
	}	
	
	@Test
	public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
	    Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
	    new StartUI(input, tracker).init();     //   создаём StartUI и вызываем метод init()
	    assertThat(tracker.findAll()[0].getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
	}

	 @Test
	 public void whenUpdateThenTrackerHasUpdatedValue() {
		Item item = tracker.add(new Item());
		Input input = new StubInput(new String[]{"1", "2", item.getId(), "test name", "desc", "6"});
		new StartUI(input, tracker).init();
		// проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
		assertThat(tracker.findById(item.getId()).getName(), is("test name"));
	 }
	 
	 @Test
	 public void whenDeleteThenTrackerHasDeleteItem() {
		Item item = tracker.add(new Item());
		Input input = new StubInput(new String[]{"1", "0", "test name", "desc", "0", "test2 name", "desc2", "3",  item.getId(), "6"});
		// создаём StartUI и вызываем метод init()
		new StartUI(input, tracker).init();
		// проверяем, что удалился элемент item из трекера.
		assertThat(tracker.findAll().length, is(2));
	 }
	 
	 @Test
	 public void whenChooseShowAllThenShowAllInMemory() {
		 Input input = new StubInput(new String[]{"0", "test name", "desc", "0", "test2 name", "desc2", "6"});
		 new StartUI(input, tracker).init();
		 assertThat(tracker.findAll()[1].getName(), is(
					new StringBuilder()
								.append("test2 name")
                                .toString()
		 ));
	 }
}