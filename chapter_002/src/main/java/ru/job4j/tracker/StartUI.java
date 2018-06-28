package ru.job4j.tracker;

import ru.job4j.models.*;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StartUI {
    /**
     * Константа меню для добавления новой заявки.
     */
   // private static final String ADD = "0";
	
	/**
     * Константа меню для отображения всех завявок.
     */
    //private static final String SHOW = "1";
	
	/**
     * Константа меню для редактирования заявки.
     */
   // private static final String EDIT = "2";
	
	/**
     * Константа меню для удаления заявки.
     */
   // private static final String DELETE = "3";
	
	/**
     * Константа меню для поиска заявки по id.
     */
  // private static final String FIND_ID = "4";
	
	/**
     * Константа меню для поиска заявки по имени.
     */
    //private static final String FIND_NAME = "5";

    /**
     * Константа для выхода из цикла.
     */
   // private static final String EXIT = "6";
    /**
     * Получение данных от пользователя.
     */
    private final Input input;

    /**
     * Хранилище заявок.
     */
    private final Tracker tracker;
	private int[] ranges = new int[] {0, 1, 2, 3, 4, 5, 6};

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
	 
    public StartUI(Input input, Tracker tracker) {

        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
		Tracker tracker = new Tracker();
		MenuTracker menu = new MenuTracker(input, tracker);
		menu.fillActions();
		do {
			menu.show();
			int key = Integer.valueOf(input.ask("Select:"));
			menu.select(input.ask("select:", ranges));
		} while (!"y".equals(input.ask("Exit? y")));
		
        //boolean exit = false;
       // while (!exit) {
       //     this.showMenu();
       //     String answer = this.input.ask("Enter the element menu : ");
        //    if (ADD.equals(answer)) {
        //        this.createItem();
        //    } else if (SHOW.equals(answer)) {
		//		this.showAll();
		//	} else if (EDIT.equals(answer)) {
		//		this.editItem();
		//	} else if (DELETE.equals(answer)) {
		//		this.deleteItem();	
		//	} else if (FIND_ID.equals(answer)) {
		//		this.findId();	
		//	} else if (FIND_NAME.equals(answer)) {
		//		this.findName();
        //    } else if (EXIT.equals(answer)) {
        //        exit = true;
        //    }
        //}
    }
	
	/**
     * Метод реализует вывод всех заявок из хранилища в консоль.
     */
	private void showAll() {
		System.out.println("------------ All items --------------");
		for (Item item : tracker.findAll()) {
			System.out.println("------------  Item with getId : " + item.getId() + "-----------" + item.getName() + ", desct: " + item.getDesc());
		}
	}
	
	/**
     * Метод реализует редактирование заявки в хранилище.
     */
	private void editItem() {
		System.out.println("------------ Edit item --------------");
		String id = input.ask("Enter item id  :");
		String name = input.ask("Enter item name :");
        String desc = input.ask("Enter item description :");
        Item item = new Item(name, desc);
		tracker.replace(id, item);
	}
	
	/**
     * Метод реализует удаление заявки из хранилища.
     */
	private void deleteItem() {
		System.out.println("------------ Delete item --------------");
		String id = input.ask("Enter item id :");
		tracker.delete(id);		
	}
	
	/**
     * Метод реализует поиск по айди заявки в хранилище.
     */
	private void findId() {
		System.out.println("------------ Search item by id --------------");
		String id = input.ask("Enter item id :");
		System.out.println("------------ Found item with getId : " + tracker.findById(id).getId() + "-----------" + tracker.findById(id).getName());
	}
	
	/**
     * Метод реализует поиск по имени заявки в хранилище.
     */
	private void findName() {
		System.out.println("------------ Search item by name --------------");
		String name = input.ask("Enter item name :");

		System.out.println("------------ Found item with getId : " + tracker.findByName(name).getId() + "-----------" + tracker.findByName(name).getName());
	}
	
	/**
     * Метод реализует добавленяи новый заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Add new item --------------");
        String name = input.ask("Enter item name :");
        String desc = input.ask("Enter item description :");
        Item item = new Item(name, desc);
        tracker.add(item);
        System.out.println("------------ New item with getId : " + item.getId() + "-----------");
    }
	
	/**
     * Метод реализует отображение меню.
     */
    private void showMenu() {
        System.out.println("Menu:");
        // добавить остальные пункты меню.
		System.out.println("0. Add new Item");
		System.out.println("1. Show all items");
		System.out.println("2. Edit item");
		System.out.println("3. Delete item");
		System.out.println("4. Find item by Id");
		System.out.println("5. Find items by name");
		System.out.println("6. Exit Program");
		System.out.println("Select:");
    }

    /**
     * Запускт программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }
}