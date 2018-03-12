package ru.job4j.tracker.start;

import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.models.Task;

import java.util.ArrayList;

/**
 * Class MenuTracker.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.10.2017
 */
public class MenuTracker {
    /**
     * Массив ответов.
     */
    private ArrayList<Integer> ranges = new ArrayList<>();
    /**
     * Приветная переменная для объекта Input.
     */
    private Input input;
    /**
     * Приватная переменная для объекта Tracker.
     */
    private Tracker tracker;

    /**
     * Лист для реализаций интерфейсов.
     */
    private ArrayList<UserAction> actions = new ArrayList<>();

    /**
     * Конструкторм класса MenuTracker.
     *
     * @param input   принимаем ссылку на объект Input.
     * @param tracker принимаем ссылку на объект Tracker.
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Геттер для массива.
     *
     * @return вернем ссылку на массив.
     */
    public ArrayList<Integer> getRanges() {
        for (UserAction action : actions) {
            ranges.add(action.key());
        }
        return ranges;
    }

    /**
     * Метод для инициализации массива.
     */
    public void fillActions() {
        this.actions.add(new AddItem("Add new Item.", 0));
        this.actions.add(new ShowItems("Show all items.", 1));
        this.actions.add(new EditItem("Edit item.", 2));
        this.actions.add(new DeleteItem("Delete item.", 3));
        this.actions.add(new FindItemById("Find item by Id.", 4));
        this.actions.add(new FindItemByName("Find items by name.", 5));
    }

    /**
     * Метод для вызова необходимой реализации интерфейса из массива.
     *
     * @param key принимае номер пункта меню.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Метод для отображения меню.
     */
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Реализация пункта меню Add new Item.
     */
    private static class AddItem extends BaseAction {
        /**
         * Конструктор для класса.
         *
         * @param menuName принимаем имя меню.
         * @param key      принимаем номер меню.
         */
        private AddItem(String menuName, int key) {
            super(menuName, key);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Пожалуйста введите имя задачи: ");
            String desc = input.ask("Пожалуйста введите дескрипшион: ");
            tracker.add(new Task(name, desc));
        }
    }

    /**
     * Реализация пункта меню Show all items.
     */
    private static class ShowItems extends BaseAction {
        /**
         * Конструктор для класса.
         *
         * @param menuName принимаем имя меню.
         * @param key      принимаем номер меню.
         */
        private ShowItems(String menuName, int key) {
            super(menuName, key);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            for (Item item : tracker.getAll()) {
                System.out.println(String.format("%s. %s", item.getId(), item.getName()));
            }
        }
    }

    /**
     * Реализация пункта меню Edit item.
     */
    private static class EditItem extends BaseAction {
        /**
         * Конструктор для класса.
         *
         * @param menuName принимаем имя меню.
         * @param key      принимаем номер меню.
         */
        private EditItem(String menuName, int key) {
            super(menuName, key);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            Item item = tracker.findById(input.ask("Введите id заявки которую необходимо отредактировать: "));
            Item newItem = new Task(input.ask("Введите имя заявки: "), input.ask("Введите дескприптор: "));
            newItem.setId(item.getId());
            tracker.update(newItem);
        }
    }

    /**
     * Реализация пункта меню Delete item.
     */
    private static class DeleteItem extends BaseAction {
        /**
         * Конструктор для класса.
         *
         * @param menuName принимаем имя меню.
         * @param key      принимаем номер меню.
         */
        private DeleteItem(String menuName, int key) {
            super(menuName, key);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            Item item = tracker.findById(input.ask("Введите id заявки которую необходимо удалить: "));
            tracker.delete(item);
        }
    }

    /**
     * Реализация пункта меню Find item by Id.
     */
    private static class FindItemById extends BaseAction {
        /**
         * Конструктор для класса.
         *
         * @param menuName принимаем имя меню.
         * @param key      принимаем номер меню.
         */
        private FindItemById(String menuName, int key) {
            super(menuName, key);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            Item item = tracker.findById(input.ask("Введите id для поиска заявки: "));
            System.out.println(item.toString());
        }
    }

    /**
     * Реализация пункта меню Find items by name.
     */
    private static class FindItemByName extends BaseAction {
        /**
         * Конструктор для класса.
         *
         * @param menuName принимаем имя меню.
         * @param key      принимаем номер меню.
         */
        private FindItemByName(String menuName, int key) {
            super(menuName, key);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            ArrayList<Item> items = tracker.findByName(input.ask("Введите имя заявки которую хотите найти: "));
            for (Item item : items) {
                System.out.println(item.toString());
            }
        }
    }

}
