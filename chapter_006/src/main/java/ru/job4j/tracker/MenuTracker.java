package ru.job4j.tracker;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class MenuTracker {

    private final Input input;
    private final Tracker tracker;
    private final UserAction[] actions = new UserAction[8];
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
        int index = 0;
        this.actions[index] = new ShowAllItems(index++, "Show all items");
        this.actions[index] = new AddItem(index++, "Add new Item");
        this.actions[index] = new AddComment(index++, "Add new comment");
        this.actions[index] = new EditItem(index++, "Edit item");
        this.actions[index] = new DeleteItem(index++, "Delete item");
        this.actions[index] = new FindId(index++, "Find item by Id");
        this.actions[index] = new FindName(index++, "Find items by name");
        this.actions[index] = new Exit(index, "Exit program");
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

    private class Print {
        public void print(String name, List<Item> list) {
            System.out.println(String.format("------------%s-----------------", name));
            boolean flag = true;
            for (Item iter : list) {
                System.out.println("ID: " + iter.getId());
                System.out.println("Name: " + iter.getName());
                System.out.println("Description: " + iter.getDesc());
                System.out.println("Time: " + iter.getCreated());
                for (String st : iter.getComments()) {
                    System.out.println(String.format("Comments: %s", st));
                }
                System.out.println("--------------------------------------");
                flag = false;
            }
            if (flag) {
                System.out.println("Empty...");
                System.out.println("--------------------------------------");
            }
        }
    }

    private class ShowAllItems extends BaseAction {

        public ShowAllItems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            new Print().print("All Items", tracker.findAll());
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
            System.out.println("--------------------------------------");
            System.out.println();
        }
    }

    private class AddComment extends BaseAction {

        public AddComment(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Add comment--------------");
            String id = input.ask("Enter ID: ");
            String comment = input.ask("Comment: ");
            tracker.addCom(id, comment);
            System.out.println("-------------------------------------");
            System.out.println();
        }
    }

    private class EditItem extends BaseAction {

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

    private class DeleteItem extends BaseAction {

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

    private class FindId extends BaseAction {

        public FindId(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Find Item by ID-----------");
            String answer = input.ask("Enter ID: ");
            new Print().print("Find item", tracker.findById(answer));
        }
    }

    private class FindName extends BaseAction {

        public FindName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Find Item by Name---------");
            String answer = input.ask("Enter Item name: ");
            new Print().print("Find items", tracker.findByName(answer));
        }
    }

    private class Exit extends BaseAction {

        public Exit(int key, String name) {
            super(key, name);
            menuExit = key;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            tracker.exit();
        }
    }
}
