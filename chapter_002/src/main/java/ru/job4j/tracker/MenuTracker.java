package ru.job4j.tracker;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

class EditItem extends BaseAction {

    public EditItem(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------Edit Item-----------------");
        String answer = input.ask("Enter ID: ");
        String name = input.ask("New name: ");
        String desc = input.ask("New description: ");
        tracker.replace(answer, new Item(name, desc));
        System.out.println("--------------------------------------");
    }
}

class DeleteItem extends BaseAction {

    public DeleteItem(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------Delete Item---------------");
        String answer = input.ask("Enter ID: ");
        tracker.delete(answer);
        System.out.println("--------------------------------------");
    }
}

public class MenuTracker {

    private Input input;
    private Tracker tracker;
    private UserAction[] actions = new UserAction[7];
    private int menuExit;

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public int[] getMenuRanges() {
        int[] menuRanges = new int[this.actions.length];
        for (int i = 0; i < this.actions.length; i++) {
            if (this.actions[i] != null) {
                menuRanges[i] = i;
            }
        }
        return menuRanges;
    }

    public int getMenuExit() {
        return menuExit;
    }

    public void fillActions() {
        this.actions[0] = new AddItem(0, "Add new Item");
        this.actions[1] = new ShowAllItems(1, "Show all items");
        this.actions[2] = new EditItem(2, "Edit item");
        this.actions[3] = new DeleteItem(3, "Delete item");
        this.actions[4] = new FindId(4, "Find item by Id");
        this.actions[5] = new FindName(5, "Find items by name");
        this.actions[6] = new Exit(6, "Exit Program");
        menuExit = 6;
    }

    public void select(int key) {
        if (this.actions[key] != null) {
            this.actions[key].execute(this.input, this.tracker);
        }
    }
    
    public void show() {
        System.out.println("Menu:");
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    private class AddItem extends BaseAction {

        public AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Add new Item--------------");
            String name = input.ask("Name: ");
            String desc = input.ask("Description: ");
            tracker.add(new Item(name, desc));
            System.out.println();
        }
    }

    private class Exit extends BaseAction {

        public Exit(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
        }
    }

    private class FindId extends BaseAction {

        public FindId(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Find Item by ID-----------");
            String answer = input.ask("Enter ID: ");
            Item item = tracker.findById(answer);
            boolean flag = true;
            if (item != null) {
                System.out.println("ID: " + item.getId());
                System.out.println("Name: " + item.getName());
                System.out.println("Description: " + item.getDesc());
                flag = false;
            }
            if (flag) {
                System.out.println("Not found...");
            }
            System.out.println("--------------------------------------");
        }
    }

    private static class ShowAllItems extends BaseAction {

        public ShowAllItems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------All Items-----------------");
            boolean flag = true;
            for (Item iter : tracker.findAll()) {
                System.out.println("ID: " + iter.getId());
                System.out.println("Name: " + iter.getName());
                System.out.println("Description: " + iter.getDesc());
                System.out.println("--------------------------------------");
                flag = false;
            }
            if (flag) {
                System.out.println("Empty...");
                System.out.println("--------------------------------------");
            }
        }
    }

    private static class FindName extends BaseAction {

        public FindName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Find Item by Name---------");
            String answer = input.ask("Enter Item name: ");
            List<Item> item = tracker.findByName(answer);
            boolean flag = true;
            for (Item iter : item) {
                System.out.println("ID: " + iter.getId());
                System.out.println("Name: " + iter.getName());
                System.out.println("Description: " + iter.getDesc());
                flag = false;
            }
            if (flag) {
                System.out.println("Not found...");
            }
            System.out.println("--------------------------------------");
        }
    }
}
