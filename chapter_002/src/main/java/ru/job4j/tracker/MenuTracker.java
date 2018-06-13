package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий удаление выбранной заявки из трекера
 */
class DeleteItem extends BaseAction {
    public DeleteItem(int key, String name) {
        super(key, name);
    }

    public void execute(Input input, Tracker tracker) {
        System.out.println("------------ Удаление заявки --------------");
        String question = input.ask("Введите ID удаяемой заявки :");
        Item item = tracker.findById(question);
        if (item != null) {
            tracker.delete(question);
        } else {
            System.out.println("Заявка не найдена, выберите другой ID");
        }
    }
}

/**
 * Класс, реализующий все функции меню
 *
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */
public class MenuTracker {
    private Input input;
    private Tracker tracker;
    public ArrayList<UserAction> actions = new ArrayList<>();

    MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Функция, заполняющая хранилище возможных действий пользователя
     *
     * @return общее количество пунктов меню
     */
    public int fillAction() {
        String nameAddItem = "Добавление новой заявки";
        this.actions.add(new MenuTracker.AddItem(0, nameAddItem));
        String nameShowItems = "Показать все заявки";
        this.actions.add(new MenuTracker.ShowItems(1, nameShowItems));
        String nameEditItem = "Редактирование заявки";
        this.actions.add(new EditItem(2, nameEditItem));
        String nameDeleteItem = "Удаление заявки";
        this.actions.add(new DeleteItem(3, nameDeleteItem));
        String nameSearchById = "Поиск заявки по ID";
        this.actions.add(new SearchById(4, nameSearchById));
        String nameSearchByName = "Поиск заявки по имени";
        this.actions.add(new SearchByName(5, nameSearchByName));
        String nameExist = "Выход из программы";
        this.actions.add(new Exit(6, nameExist));
        return actions.size();
    }

    /**
     * Функция, реализующее конкретное пользовательское действие
     *
     * @param key ключ действия
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Функция вывода меню
     */
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Класс реализует добавленяи новый заявки в хранилище.
     */
    private class AddItem extends BaseAction {
        public AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Добавление новой заявки --------------");
            String name = input.ask("Введите имя заявки :");
            String description = input.ask("Введите описание заявки :");
            long time = 123L;
            Item item = new Item(name, description, time);
            tracker.add(item);
            System.out.println("------------ Добавленна новая заявка с ID: " + item.getId() + "-----------");

        }
    }

    /**
     * Класс, реализующий изменение заявки в трекере
     */
    private static class EditItem extends BaseAction {

        private EditItem(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, Tracker tracker) {
            if (tracker.findAll().isEmpty()) {
                System.out.println("Список заявок пуст");
            } else {
                System.out.println("-----------------Изменение заявки--------------------");
                String id = input.ask("Введите id заявки, которую нужно изменить: ");
                String name = input.ask("Введите имя новой заявки: ");
                String desc = input.ask("Введите описание новой заявки: ");
                Item item = new Item(name, desc, 123L);
                tracker.replace(id, item);
                System.out.println("------------------Изменена заявка ID " + id + "-------------------");
            }
        }
    }

    /**
     * Класс, реализующий просмотр всех заявок в трекере
     */
    private class ShowItems extends BaseAction {

        private ShowItems(int key, String note) {
            super(key, note);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            List<Item> items = tracker.findAll();
            System.out.println("Список всех заявок:" + items.size());
            for (Item item : items) {
                System.out.println("Имя: " + item.getName() + " ID: " + item.getId());
            }
        }
    }

    /**
     * Класс, реализующий поиск заявки в трекере по id
     */
    private class SearchById extends BaseAction {

        private SearchById(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, Tracker tracker) {
            if (tracker.findAll().isEmpty()) {
                System.out.println("Список заявок пуст");
            } else {
                System.out.println("-------------------Поиск заявки--------------------");
                String id = input.ask("Введите id заявки, которую нужно найти: ");
                Item item = tracker.findById(id);
                if (item != null) {
                    System.out.println("Заявка id: " + item.getId() + ", name: " + item.getName()
                            + ", description: " + item.getDescription() + ", created: " + item.getCreate());
                } else {
                    System.out.println("Заявка не найдена");
                }
                System.out.println("-------------------Поиск заявки завершен----------------------");
            }
        }
    }

    /**
     * Класс, реализующий поиск заявок в трекере по имени
     */
    private class SearchByName extends BaseAction {

        private SearchByName(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, Tracker tracker) {
            if (tracker.findAll().isEmpty()) {
                System.out.println("Список заявок пуст");
            } else {
                System.out.println("---------------------Поиск заявок по имени---------------------");
                String name = input.ask("Введите name заявки, которую нужно найти: ");
                List<Item> items = tracker.findByName(name);
                if (items.size() > 0) {
                    for (Item item : items) {
                        System.out.println("Заявка id: " + item.getId() + ", name: " + item.getName()
                                + ", description: " + item.getDescription() + ", created: " + item.getCreate());
                    }
                } else {
                    System.out.println("Заявки с именем " + name + " не найдены");
                }
                System.out.println("---------------------Конец списка-------------------------");
            }
        }
    }

    /**
     * Класс реализует выход из программы
     */
    private class Exit extends BaseAction {

        private Exit(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, Tracker tracker) {
        }
    }
}
