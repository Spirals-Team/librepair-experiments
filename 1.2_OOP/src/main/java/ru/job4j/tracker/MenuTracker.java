package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 *Класс FindItemById.
 */
class FindItemById extends BaseAction {
	/**
	 * Конструктор.
	 * @param key ключ
	 * @param name описание
	 */
	FindItemById(int key, String name) {
		super(key, name);
	}
	/**
	 * Основной метод. Ищет заявку по id.
	 * @param input объект типа Input
	 * @param tracker объект типа Tracker
	 */
	public void execute(Input input, Tracker tracker) {
		String id = input.ask("Please, enter id of task you want to see: ");
		System.out.println(tracker.findById(id) + "\n");
	}
}
/**
 *Класс FindItemsByName.
 */
class FindItemsByName extends BaseAction {
	/**
	 * Конструктор.
	 * @param key ключ
	 * @param name описание
	 */
	FindItemsByName(int key, String name) {
		super(key, name);
	}
	/**
	 * Основной метод. Ищет заявки по name.
	 * @param input объект типа Input
	 * @param tracker объект типа Tracker
	 */
	public void execute(Input input, Tracker tracker) {
		String name = input.ask("Please, enter name of tasks you want to see: ");
		List<Item> items = tracker.findByName(name);
		System.out.println("List of all added tasks with name:" + name);
		for (Item item : items) {
			System.out.println(item);
		}
		System.out.println();
	}
}

/**
 * Класс MenuTracker. Основной класс в файле.
 *@author ifedorenko
 *@since 28.08.2017
 *@version 1
 */
public class MenuTracker {
	/**
	 * @param хранит ссылку на объект .
	 */
	private Input input;
	/**
	 * @param хранит ссылку на объект .
	 */
	private Tracker tracker;
	/**
	 * @param хранит ссылку на массив типа UserAction.
	 */
	private List<BaseAction> actions = new ArrayList<>();

	/**
	 * Конструктор.
	 *
	 * @param input   объект типа Input
	 * @param tracker объект типа Tracker
	 */
	public MenuTracker(Input input, Tracker tracker) {
		this.input = input;
		this.tracker = tracker;
	}

	/**
	 * Метод для получения массива меню.
	 *
	 * @return длину массива
	 */
	public int getActionsLentgh() {
		return this.actions.size();
	}

	/**
	 * Метод заполняет массив.
	 */
	public void fillActions() {
		this.actions.add(new AddItem(0, "Add program"));
		this.actions.add(new ShowItems(1, "Show all items"));
		this.actions.add(new MenuTracker.EditItem(2, "Edit item"));
		this.actions.add(new MenuTracker.DeleteItem(3, "Delete item"));
		this.actions.add(new FindItemById(4, "Find item by Id"));
		this.actions.add(new FindItemsByName(5, "Find items by name"));
		this.actions.add(new ExitProgram(6, "Exit Program"));
	}

	/**
	 * Метод в зависимости от указанного ключа, выполняет соотвествующие действие.
	 *
	 * @param key ключ операции
	 */
	public void select(int key) {
		this.actions.get(key).execute(this.input, this.tracker);
	}

	/**
	 * Метод выводит на экран меню.
	 */
	public void show() {
		for (UserAction action : this.actions) {
			if (action != null) {
				System.out.println(action.info());
			}
		}
	}

	/**
	 * Внутренний класс ExitProgram.
	 */
	private class ExitProgram extends BaseAction {
		/**
		 * Конструктор.
		 *
		 * @param key  ключ
		 * @param name описание
		 */
		ExitProgram(int key, String name) {
			super(key, name);
		}

		/**
		 * Основной метод. Выход из программы.
		 *
		 * @param input   объект типа Input
		 * @param tracker объект типа Tracker
		 */
		public void execute(Input input, Tracker tracker) {
		}
	}

	/**
	 * Внутренний класс AddItem.
	 */
	private class AddItem extends BaseAction {
		/**
		 * Конструктор.
		 *
		 * @param key  ключ
		 * @param name описание
		 */
		AddItem(int key, String name) {
			super(key, name);
		}

		/**
		 * Основной метод. Добавляет заявку.
		 *
		 * @param input   объект типа Input
		 * @param tracker объект типа Tracker
		 */
		public void execute(Input input, Tracker tracker) {
			String name = input.ask("Please, enter the task's name: ");
			String desc = input.ask("Please, enter the task's desc: ");
			long curTime = System.currentTimeMillis();
			String id = tracker.add(new Item(name, desc, curTime)).getId();
			System.out.println("In system added new task with id: " + id + "\n");
		}
	}

	/**
	 * Внутренний класс ShowItems.
	 */
	private class ShowItems extends BaseAction {
		/**
		 * Конструктор.
		 *
		 * @param key  ключ
		 * @param name описание
		 */
		ShowItems(int key, String name) {
			super(key, name);
		}

		/**
		 * Основной метод. Показывает список добавленных заявок.
		 *
		 * @param input   объект типа Input
		 * @param tracker объект типа Tracker
		 */
		public void execute(Input input, Tracker tracker) {
			System.out.println("List of all added tasks:");
			for (Item item : tracker.getItems()) {
				System.out.printf("%s, %s", item.getId(), item.getName() + "\n");
			}
		}
	}

	/**
	 * Вложенный класс EditItem.
	 */
	private static class EditItem extends BaseAction {
		/**
		 * Конструктор.
		 *
		 * @param key  ключ
		 * @param name описание
		 */
		EditItem(int key, String name) {
			super(key, name);
		}

		/**
		 * Основной метод. Изменяет заявку.
		 *
		 * @param input   объект типа Input
		 * @param tracker объект типа Tracker
		 */
		public void execute(Input input, Tracker tracker) {
			String id = input.ask("Enter id of task you want to edit: ");
			String name = input.ask("Please, enter the task's name: ");
			String desc = input.ask("Please, enter the task's desc: ");
			long curTime = System.currentTimeMillis();
			Item item = new Item(name, desc, curTime);
			item.setId(id);
			tracker.update(item);
			System.out.println("Ваша заявка отредактирована\n");
		}
	}

	/**
	 * Вложенный класс DeleteItem.
	 */
	private static class DeleteItem extends BaseAction {
		/**
		 * Конструктор.
		 *
		 * @param key  ключ
		 * @param name описание
		 */
		DeleteItem(int key, String name) {
			super(key, name);
		}
		/**
		 * Основной метод. Удаляет заявку.
		 * @param input   объект типа Input
		 * @param tracker объект типа Tracker
		 */
		public void execute(Input input, Tracker tracker) {
			String id = input.ask("Please, enter id of task you want to delete: ");
			tracker.delete(tracker.findById(id));
			System.out.println("Task is delete \n");
		}
	}
}