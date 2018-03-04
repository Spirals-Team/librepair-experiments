package ru.job4j.tracker;
/**
 * StartUI.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class StartUI {
    /*** Input.*/
    private Input input;
    /*** Tracker.*/
    private Tracker tracker;
    /**
     * Constructor.
     * @param input - input
     * @param tracker - tracker
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }
    /***  Tracker menu.*/
    public void init() {
        /*
        0. Add new Item
        1. Show all items
        2. Edit item
        3. Delete item
        4. Find item by Id
        5. Find items by name
        Select:
        */
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        do {
            menu.show();
            menu.select(input.ask("Select: ", menu.getRange()));
        } while (!"y".equals(input.ask("Exit? (y): ")));
    }
    /**
     * Main method.
     * @param args - args
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(), new Tracker()).init();
    }
}
