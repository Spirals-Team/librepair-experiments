package ru.job4j.tracker;

public class StartUI implements Stop {
    private int[] ranges = new int[] {0, 1, 2, 3, 4, 5, 6};
    private final Input input;
    private final Tracker tracker;
    private boolean working = true;

    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void startWorking() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions(this);
        menu.add(new MenuTracker.Exit(6, "Exit.", this));

        do {
            menu.show();
            menu.select(input.ask("Sepect: ", ranges));
        }
        while (this.working);
    }

    public void stop() {
        this.working = false;
    }

    public static void main(String[] args) {
        new StartUI(
                new ValidateInput(
                        new ConsoleInput()
                ),
                new Tracker()
        ).startWorking();
    }
}
