package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class MenuTracker {
    private int[] ranges = new int[] {0, 1, 2, 3, 4, 5, 6};
    private Input input;
    private Tracker tracker;
    private List<UserAction> actions = new ArrayList<>();

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public int[] getRanges() {
        return ranges;
    }

    public void fillActions() {
        actions.add(this.new AddItem(0, "Добавление новой заявки"));
        actions.add(this.new ShowItems(1, "Показать все заявки"));
        actions.add(this.new EditItem(2, "Редактирование заявки"));
        actions.add(new MenuTracker.DeleteItems(3, "Удаление заявки"));
        actions.add(new MenuTracker.FindBiID(4, "Поиск заявки по id"));
        actions.add(new MenuTracker.FindBiName(5, "Поиск заявки по имени"));
        actions.add(new ExitProgram(6, "Выход их программы"));
    }

    public boolean select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
        boolean showMenu = true;
        if (key == 6) {
            showMenu = false;
        }
        return showMenu;
    }

    public void show() {
        for (UserAction action : this.actions) {
            System.out.println(action.info());
        }
    }

    private class AddItem extends BaseAction {
        protected AddItem(int key, String name) {
            super(key, name);
        }

        public int key() {
            return 0;
        }

        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Введите имя заявки: ");
            String desc = input.ask("Введите описание: ");
            tracker.add(new Item(name, desc, System.currentTimeMillis()));
        }
    }

    private class ShowItems extends BaseAction {
        protected ShowItems(int key, String name) {
            super(key, name);
        }

        public int key() {
            return 1;
        }

        public void execute(Input input, Tracker tracker) {
            for (Item item : tracker.findAll()) {
                System.out.println(String.format("Id заявки: %s%nИмя заявки: %s%nОписание заявки: %s%nВремя создания заявки: %s%n",
                                                    item.getId(), item.getName(), item.getDescription(), item.getCreate()));
            }
        }
    }

    private class EditItem extends BaseAction {
        protected EditItem(int key, String name) {
            super(key, name);
        }

        public int key() {
            return 2;
        }

        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите id редактируемой заявки: ");
            String name = input.ask("Введите новое имя заявки: ");
            String desc = input.ask("Введите новое описание заявки: ");
            Item item = new Item(name, desc, System.currentTimeMillis());
            tracker.replace(id, item);
        }
    }

    private static class DeleteItems extends BaseAction {
        protected DeleteItems(int key, String name) {
            super(key, name);
        }

        public int key() {
            return 3;
        }

        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите id удаляемой заявки: ");
            tracker.delete(id);
        }
    }

    private static class FindBiID extends BaseAction {
        protected FindBiID(int key, String name) {
            super(key, name);
        }

        public int key() {
            return 4;
        }

        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите id искомой заявки: ");
            Item item = tracker.findById(id);
            System.out.println(String.format("Id заявки: %s%nИмя заявки: %s%nОписание заявки: %s%nВремя создания заявки: %s%n",
                    item.getId(), item.getName(), item.getDescription(), item.getCreate()));
        }
    }

    private static class FindBiName extends BaseAction {
        protected FindBiName(int key, String name) {
            super(key, name);
        }

        public int key() {
            return 5;
        }

        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Введите имя искомой заявки: ");
            for (Item item : tracker.findByName(name)) {
                System.out.println(String.format("Id заявки: %s%nИмя заявки: %s%nОписание заявки: %s%nВремя создания заявки: %s%n",
                        item.getId(), item.getName(), item.getDescription(), item.getCreate()));
            }
        }
    }
}

class ExitProgram extends BaseAction {
    protected ExitProgram(int key, String name) {
        super(key, name);
    }

    public int key() {
        return 6;
    }

    public void execute(Input input, Tracker tracker) {

    }
}
