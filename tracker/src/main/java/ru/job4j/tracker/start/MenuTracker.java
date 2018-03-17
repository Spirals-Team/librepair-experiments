package ru.job4j.tracker.start;

import ru.job4j.tracker.action.BaseAction;
import ru.job4j.tracker.action.UserAction;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuTracker.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.09.17;
 * @version $Id$
 * @since 0.1
 */

public class MenuTracker {
    private static final String EXIT = "exit";
    /**
     * Input.
     */
    private Input input;
    /**
     * Tracker.
     */
    private Tracker tracker;
    /**
     * Массив действий пользователя.
     */
    private ArrayList<UserAction> actions = new ArrayList<>();
    /**
     * Конструктор класса.
     * @param input input.
     * @param tracker tracker.
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }
    /**
     * Заполняет массив действий всеми возможными действиями.
     */
    public void fillActions() {
        this.actions.add(new AddItem("Add Item.", 0));
        this.actions.add(new ShowAllItems("Show all Items.", 1));
        this.actions.add(new EditItem("Edit item.", 2));
        this.actions.add(new DeleteItem("Delete Item.", 3));
        this.actions.add(new FindById("Find by Id.", 4));
        this.actions.add(new FindByName("Find by Name.", 5));
        this.actions.add(new Exit("Exit.", 6));
    }

    /**
     * Метод возвращает массив с номерами возможных действий.
     * @return keys.
     */
    public int[] rangs() {
        int[] rang = new int[actions.size()];
        for (int i = 0; i < actions.size(); i++) {
            rang[i] = actions.get(i).key();
        }
        return rang;
    }

    /**
     * Метод добовляет новое действие в меню.
     * @param action действие.
     */
    public void addAction(UserAction action) {
        this.actions.add(action);
    }

    /**
     * Выводит информацию об действии.
     */
    public void show() {
        actions.forEach(actoin -> System.out.println(actoin.info()));
    }

    /**
     * В зависимости от ключа выполняет соответствующее действие.
     * @param key ключ.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.tracker, this.input);
    }

    /**
     * Внутренний класс для добоавления элементов.
     */
    private class AddItem extends BaseAction {
        /**
         * Конструктор класса.
         * @param name имя подменю.
         * @param key номер подменю.
         */
        AddItem(String name, int key) {
            super(name, key);
        }

        /**
         * Исполнение добовления.
         * @param tracker tracker.
         * @param input input.
         */
        @Override
        public void execute(Tracker tracker, Input input) {
            String name = input.ask("Enter name:");
            String desc = input.ask("Enter description:");
            Item item = tracker.add(new Item(name, desc));
            input.writeMessage(String.format("Your item has been saved for id: %s", item.getId()));
        }
    }

    /**
     * Класс описывает поведение при показе всех элементов.
     */
    private static class ShowAllItems extends BaseAction {
        /**
         * Конструктор класса.
         * @param name имя подменю.
         * @param key номер подменю.
         */
        ShowAllItems(String name, int key) {
            super(name, key);
        }

        @Override
        public void execute(Tracker tracker, Input input) {
            tracker.getAll().forEach(System.out::println);
        }
    }

    /**
     * Класс удаления заявки.
     */
    private class DeleteItem extends BaseAction {
        /**
         * Конструктор класса.
         * @param name имя подменю.
         * @param key номер подменю.
         */
        DeleteItem(String name, int key) {
            super(name, key);
        }


        /**
         * Метод удаляет заявку.
         * @param tracker tracker.
         * @param input input.
         */
        @Override
        public void execute(Tracker tracker, Input input) {
            boolean found = false;
            Item item = null;
            while (!found) {
                String id = input.ask("Enter id:");
                if (EXIT.equalsIgnoreCase(id)) {
                    break;
                }
                item = tracker.findById(id);
                if (item == null) {
                    System.out.println("Invalid ID enter again or tape exit:");
                } else {
                    found = true;
                }
            }
            tracker.delete(item);
            input.writeMessage("The entry was successfully deleted.");
        }
    }

    /**
     * Класс поиска по ID.
     */
    public static class FindById extends BaseAction {
        /**
         * Конструктор класса.
         * @param name имя подменю.
         * @param key номер подменю.
         */
        FindById(String name, int key) {
            super(name, key);
        }

        /**
         * Метод ищет запись по id.
         * @param tracker tracker.
         * @param input input.
         */
        @Override
        public void execute(Tracker tracker, Input input) {
            Item item = null;
            do {
                String id = input.ask("Enter id:");
                if (EXIT.equalsIgnoreCase(id)) {
                    break;
                }
                item = tracker.findById(id);
                if (item == null) {
                    System.out.println("Invalid ID enter again or tape exit:");
                }
            } while (item == null);
            if (item == null) {
                System.out.println("The request was canceled.");
            } else {
                input.writeMessage(item.toString());
                new MenuComments(tracker, input, item.getId());
            }
        }
    }

    /**
     * Класс поиска по имени заявки.
     */
    private class FindByName extends BaseAction {
        /**
         * Конструктор класса.
         * @param name имя подменю.
         * @param key номер подменю.
         */
        FindByName(String name, int key) {
            super(name, key);
        }

        @Override
        public void execute(Tracker tracker, Input input) {
            boolean found = false;
            List<Item> item = new ArrayList<>();
            while (!found) {
                String name = input.ask("Enter name:");
                if (EXIT.equalsIgnoreCase(name)) {
                    break;
                }
                item = tracker.findByName(name);
                if (item.size() == 0) {
                    System.out.println("Invalid name enter again or tape exit:");
                } else {
                    found = true;
                }
            }
            item.forEach(System.out ::println);
        }
    }

    /**
     * Exit.
     */
    private class Exit extends BaseAction {
        /**
         * Конструктор класса.
         * @param name имя подменю.
         * @param key номер подменю.
         */
        Exit(String name, int key) {
            super(name, key);
        }


        /**
         *Exit.
         * @param tracker tracker.
         * @param input input.
         */
        @Override
        public void execute(Tracker tracker, Input input) {
            tracker.close();
            input.writeMessage("Tracker shutdown. Goodbye!");
        }
    }

    /**
     * Класс для редактирования элементов.
     */
    public class EditItem extends BaseAction {
        /**
         * Конструктор класса.
         * @param name Имя подменю.
         * @param key номер подменю.
         */
        EditItem(String name, int key) {
            super(name, key);
        }

        /**
         * Метод обновляет запись.
         * @param tracker tracker.
         * @param input input.
         */
        @Override
        public void execute(Tracker tracker, Input input) {
            boolean found = false;
            while (!found) {
               String id = input.ask("Enter id:");
               Item item = tracker.findById(id);
                if (EXIT.equalsIgnoreCase(id)) {
                    break;
                }
                if (item == null) {
                    System.out.println("Invalid ID enter again or tape exit:");
                } else {
                    found = true;
                    String name = input.ask("Enter name:");
                    String desc = input.ask("Enter description:");
                    tracker.update(new Item(id, name, desc));
                    input.writeMessage("Item was updated successfully.");

                }
            }
        }


    }
}
