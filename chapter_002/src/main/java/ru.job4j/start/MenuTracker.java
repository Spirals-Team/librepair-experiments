package ru.job4j.start;

import java.util.ArrayList;

/**
 * 2. Написать класс MenuTracker который будет реализовывать меню.
 * 3. В классе MenuTracker реализовать внутренние классы событий:
 * добавление, редактирование, удаление, поиск по имени, поиск по id и вывод на экран всех заявок.
 * 4. При реализации событий использовать статический внутренний класс, не статический внутренний
 * класс и внешний класс, расположенный в одном файле с MenuTracker.
 */
class EditItem implements UserAction {

    @Override
    public int key() {
        return 2;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Please, enter the tasks's id: ");
        String name = input.ask("Please, enter the task's name:");
        String desc = input.ask("Please, enter the description:");
        Item item = new Item(name, desc, 123L);
        item.setId(id);
//        tracker.update(item);
        tracker.edit(item);
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Edit item.");
    }
}

class DeleteItem implements UserAction {

    @Override
    public int key() {
        return 3;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Please, enter the tasks's id: ");
        Item item = tracker.findById(id);
        System.out.println(tracker.showItem(item) + " deleted");
        tracker.delete(item);
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Delete item.");
    }
}

class FindItemById implements UserAction {

    @Override
    public int key() {
        return 4;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Please, enter the tasks's id: ");
        Item item = tracker.findById(id);
        System.out.println(tracker.showItem(item));
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Find item by id.");
    }
}

class FindItemsByName implements UserAction {

    @Override
    public int key() {
        return 5;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String name = input.ask("Please, enter the name: ");
//        Item[] items = tracker.findByName(name);
        ArrayList<Item> items = tracker.findByName(name);
        for (Item item : items) {
            System.out.println(tracker.showItem(item));
        }
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Find items by name.");
    }
}

public class MenuTracker {

    private Input input;
    private Tracker tracker;
//    private UserAction[] actions = new UserAction[6];
    private ArrayList<UserAction> actions = new ArrayList<UserAction>();
    private int position = 0;



    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void fillActions() {
//        this.actions[0] = new MenuTracker.AddItem(); //this.new AddItem();//new AddItem(this.input, this.tracker);
//        this.actions[1] = new MenuTracker.ShowItems(); //new ShowItems();
//        this.actions[2] = new EditItem();
//        this.actions[3] = new DeleteItem();
//        this.actions[4] = new FindItemById();
//        this.actions[5] = new FindItemsByName();
//        this.actions[position++] = new MenuTracker.AddItem(); //this.new AddItem();//new AddItem(this.input, this.tracker);
//        this.actions[position++] = new MenuTracker.ShowItems(); //new ShowItems();
//       this.actions[position++] = new EditItem();
//        this.actions[position++] = new DeleteItem();
//        this.actions[position++] = new FindItemById();
//        this.actions[position++] = new FindItemsByName();
    }

    public void addAction(UserAction action)  {
//        this.actions[position++] = action;
        this.actions.add(position++, action);
    }

//    public static void test() {
//        MenuTracker tr = new MenuTracker().tracker;
//        AddItem item = tr.new AddItem();
//    }

    public void select(int key) {
//        for (UserAction action : actions) {
//            if (action.key() == key) {
//                this.actions[key].execute(this.input, this.tracker);
//            }
//        }
        try {
//            this.actions[key].execute(this.input, this.tracker);
            this.actions.get(key).execute(this.input, this.tracker);
        } catch (ArrayIndexOutOfBoundsException aaa) {
            System.out.println("Please enter valid value!");
        }

    }

    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }

        }
    }

    private class AddItem implements UserAction {

//        public AddItem() {
//
//        }

        @Override
        public int key() {
            return 0;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Please, enter the task's name:");
            String desc = input.ask("Please, enter the description:");
            tracker.add(new Item(name, desc, 123L));
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Add the new item.");
        }
    }

    private static class ShowItems implements UserAction {

        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
//            for (Item item : tracker.getAll()) {
//                System.out.println(String.format("%s. %s. %s.", item.getId(), item.getName(), item.getDescription()));
//            }
            System.out.println(tracker.showAll());
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Show all items.");
        }
    }
}