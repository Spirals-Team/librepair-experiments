package ru.job4j.tracker;

import java.util.ArrayList;

public class MenuTracker {
    private Input input;
    private Tracker tracker;
    private ArrayList<UserAction> actions = new ArrayList<UserAction>(7);
    private int position = 0;

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void fillActions(StartUI ui) {
        this.actions.add(this.new AddItem(0, "Add new item."));
        this.actions.add(new MenuTracker.ShowItems(1, "Show all items."));
        this.actions.add(new EditItem(2, "Edit item."));
        this.actions.add(new DeleteItem(3, "Delete item."));
        this.actions.add(new FindItemByID(4, "Find item by id."));
        this.actions.add(new FindItemByName(5, "Find item by name."));
    }

    public void add(UserAction action) {
        this.actions.add(action);
    }

    public void select(int key) {
        this.actions.get(key).execute(input, tracker);
    }

    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    private void showItem(Item item) {
        System.out.print(" Name: " + item.getName());
        System.out.print(" Description: " + item.getDesc());
        System.out.println(" Id: " + item.getId());
    }

    private class AddItem extends BaseAction {
        public AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Please, enter the task's name: ");
            String desc = input.ask("Please, enter the task's description: ");
            tracker.add(new Item(name, desc));
        }
    }

    private class FindItemByID extends BaseAction {
        public FindItemByID(int key, String name) {
            super(key, name);
        }
        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите ID заявки :");
            Item item = tracker.findById(id);
            showItem(item);
        }
    }

    private class FindItemByName extends BaseAction {
        public FindItemByName(int key, String name) {
            super(key, name);
        }

        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Введите имя заявки :");
            ArrayList<Item> item = tracker.findByName(name);
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i) != null)
                    showItem(item.get(i));
            }
        }
    }

    protected static class Exit extends BaseAction {
        private final Stop ui;

        public Exit(int key, String name, Stop ui) {
            super(key, name);
            this.ui = ui;
        }

        public void execute(Input input, Tracker tracker) {
            this.ui.stop();
        }
    }

    private class DeleteItem extends BaseAction {
        public DeleteItem(int key, String name) {
            super(key, name);
        }

        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите ID заявки :");
            Item item = tracker.findById(id);
            tracker.delete(item);
            System.out.println("Deleted!");
        }
    }

    private static class ShowItems extends BaseAction {
        public ShowItems(int key, String name) {
            super(key, name);
        }

        public void execute(Input input, Tracker tracker) {
            for (Item item : tracker.findAll()) {
                if (item != null) {
                    System.out.println(String.format("%s | %s | %s", item.getName(), item.getDesc(), item.getId()));
                }
            }
        }
    }
}

class EditItem extends BaseAction {
    public EditItem(int key, String name) {
        super(key, name);
    }

    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Please, enter the task's id: ");
        String name = input.ask("Please, enter the task's name: ");
        String desc = input.ask("Please, enter the task's description: ");
        Item item = tracker.findById(id);
        item.setName(name);
        item.setDesc(desc);
        tracker.update(item);
        System.out.println("Updated!");
    }
}
