package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * MenuTracker.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class MenuTracker {
    /*** Input.*/
    private Input input;
    /*** Tracker.*/
    private Tracker tracker;
    /*** Array of actions.*/
    //private UserAction[] actions = new UserAction[6];
    private List<UserAction> actions = new ArrayList<>();
    /*** Range of menu items.*/
    private int[] range = {0, 1, 2, 3, 4, 5};
    /**
     * Constructor.
     * @param input - input
     * @param tracker - tracker
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }
    /**
     * get range.
     * @return - range
     */
    public int[] getRange() {
        return this.range;
    }
    /*** Fills an array of actions.*/
    public void fillActions() {
        this.actions.add(new MenuTracker.AddNewItem(0, "Add new Item"));
        this.actions.add(new MenuTracker.ShowAllItems(1, "Show all items"));
        this.actions.add(new MenuTracker.EditItem(2, "Edit item"));
        this.actions.add(new MenuTracker.DeleteItem(3, "Delete item"));
        this.actions.add(new MenuTracker.FindByName(4, "Find by name"));
        this.actions.add(new MenuTracker.FindById(5, "Find by id"));
    }
    /**
     * Select action.
     * @param key - key
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }
    /*** Show menu.*/
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /***Add new item.*/
    private static class AddNewItem extends BaseAction {
        /**
         * Constructor.
         * @param key - key
         * @param name - name
         */
        AddNewItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Enter name of new item: ");
            String desc = input.ask("Enter description of new item: ");
            tracker.add(new Item(name, desc, 1L));
        }
    }

    /***Show all items.*/
    private static class ShowAllItems extends BaseAction {
        /**
         * Constructor.
         * @param key - key
         * @param name - name
         */
        ShowAllItems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            for (Item item : tracker.findAll()) {
                System.out.println("[ " + item.getId() + " | " + item.getName() + " | " + item.getDescription() + " ]");
            }
        }
    }

    /***Edit item.*/
    private static class EditItem extends BaseAction {
        /**
         * Constructor.
         * @param key - key
         * @param name - name
         */
        EditItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Enter id of item: ");
            Item previous = tracker.findById(id);
            if (previous != null) {
                String name = input.ask("Enter new name of item: ");
                String desc = input.ask("Enter new description of item: ");
                Item next = new Item(name, desc, 1L);
                next.setId(previous.getId());
                tracker.update(next);
            }
        }
    }

    /***Delete item.*/
    private static class DeleteItem extends BaseAction {
        /**
         * Constructor.
         * @param key - key
         * @param name - name
         */
        DeleteItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Enter id of item: ");
            Item item = tracker.findById(id);
            if (item != null) {
                tracker.delete(item);
            }
        }
    }

    /***Find by name.*/
    private static class FindByName extends BaseAction {
        /**
         * Constructor.
         * @param key - key
         * @param name - name
         */
        FindByName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Enter name of item: ");
            for (Item item : tracker.findByName(name)) {
                System.out.println("[ " + item.getId() + " | " + item.getName() + " | " + item.getDescription() + " ]");
            }
        }
    }

    /***Find by id.*/
    private static class FindById extends BaseAction {
        /**
         * Constructor.
         * @param key - key
         * @param name - name
         */
        FindById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Enter id of item: ");
            Item item = tracker.findById(id);
            if (item != null) {
                System.out.println("[ " + item.getId() + " | " + item.getName() + " | " + item.getDescription() + " ]");
            }
        }
    }
}
