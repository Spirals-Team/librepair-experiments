package ru.job4j.tracker.start;

import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.models.Task;

import java.util.ArrayList;

/**
 * Class StartUI.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 07.10.2017
 */
public class StartUI {
    /**
     * Ссылка.
     */
    private Input input;
    /**
     * Ссылка.
     */
    private Tracker tracker;
    /**
     * Константа.
     */
    private static final String ADD_NEW_ITEM = "0";
    /**
     * Константа.
     */
    private static final String SHOW_ALL_ITEM = "1";
    /**
     * Константа.
     */
    private static final String EDIT_ITEM = "2";
    /**
     * Константа.
     */
    private static final String DELETE_ITEM = "3";
    /**
     * Константа.
     */
    private static final String FIND_ITEM_BY_ID = "4";
    /**
     * Константа.
     */
    private static final String FIND_ITEM_BY_NAME = "5";
    /**
     * Константа.
     */
    private static final String EXIT_PROGRAM = "6";
    /**
     * Меню программы.
     */
    private static final String MENU = "0. Add new Item\n"
            + "1. Show all items\n"
            + "2. Edit item\n"
            + "3. Delete item\n"
            + "4. Find item by Id\n"
            + "5. Find items by name\n"
            + "6. Exit Program\n"
            + "Select:";

    /**
     * Инициализаяция.
     *
     * @param input   принимаем любую реализацию интерфейса.
     * @param tracker Принимаем обект трекера.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Метод для запуска программы.
     *
     * @param args входящие параметры
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(), new Tracker()).init();
    }

    /**
     * Инициализаяция.
     */
    public void init() {

        MenuTracker menuTracker = new MenuTracker(this.input, tracker);
        menuTracker.fillActions();
        do {
            menuTracker.show();
            menuTracker.select(input.ask("Select: ", menuTracker.getRanges()));
        } while (!"y".equals(this.input.ask("Exit? (y/n): ")));


//        while (!EXIT_PROGRAM.equals(this.Input.ask(MENU))) {
//            if (ADD_NEW_ITEM.equals(this.Input.getKey())) {
//                addNewItem(this.Input, tracker);
//            } else if (SHOW_ALL_ITEM.equals(this.Input.getKey())) {
//                showAllItem(tracker);
//            } else if (EDIT_ITEM.equals(this.Input.getKey())) {
//                editItem(this.Input, tracker);
//            } else if (DELETE_ITEM.equals(this.Input.getKey())) {
//                deleteItem(this.Input, tracker);
//            } else if (FIND_ITEM_BY_ID.equals(this.Input.getKey())) {
//                findItemById(this.Input, tracker);
//            } else if (FIND_ITEM_BY_NAME.equals(this.Input.getKey())) {
//                findItemByName(this.Input, tracker);
//            }
//        }
    }

    /**
     * Метод добавления новой заявки.
     *
     * @param input   ссылка на объект Input.
     * @param tracker ссылка на объект Tracker.
     */
    public void addNewItem(Input input, Tracker tracker) {
        Item item = new Task(input.ask("Введите имя заявки: "), input.ask("Введите дескприптор: "));
        tracker.add(item);
        System.out.println("Новая заявка создана!\n");
    }

    /**
     * Метод вывода всех заявок.
     *
     * @param tracker ссылка на объект Tracker.
     */
    public void showAllItem(Tracker tracker) {
        ArrayList<Item> items = tracker.findAll();
        for (Item item : items) {
            System.out.println(item.toString());
        }
        System.out.println("Список всех задач на экране!\n");
    }

    /**
     * Метод редктирования заявки.
     *
     * @param input   ссылка на объект Input.
     * @param tracker ссылка на объект Tracker.
     */
    public void editItem(Input input, Tracker tracker) {
        Item item = tracker.findById(input.ask("Введите id заявки которую необходимо отредактировать: "));
        Item newItem = new Task(input.ask("Введите имя заявки: "), input.ask("Введите дескприптор: "));
        newItem.setId(item.getId());
        tracker.update(newItem);
        System.out.println("Зявка успешно обнолена!\n");
    }

    /**
     * Метод удаления заявки.
     *
     * @param input   ссылка на объект Input.
     * @param tracker ссылка на объект Tracker.
     */
    public void deleteItem(Input input, Tracker tracker) {
        Item item = tracker.findById(input.ask("Введите id заявки которую необходимо удалить: "));
        tracker.delete(item);
        System.out.println("Зявка с данным id удалена успешно!\n");
    }

    /**
     * Метод поиска по ид.
     *
     * @param input   ссылка на объект Input.
     * @param tracker ссылка на объект Tracker.
     */
    public void findItemById(Input input, Tracker tracker) {
        Item item = tracker.findById(input.ask("Введите id для поиска заявки: "));
        System.out.println(item.toString());
        System.out.println("Заявка найдена!\n");
    }

    /**
     * Метод поиска по имени.
     *
     * @param input   ссылка на объект Input.
     * @param tracker ссылка на объект Tracker.
     */
    public void findItemByName(Input input, Tracker tracker) {
        ArrayList<Item> items = tracker.findByName(input.ask("Введите имя заявки которую хотите найти: "));
        for (Item item : items) {
            System.out.println(item.toString());
        }
        System.out.println("Все заявки с данным именем на экране\n");
    }
}
