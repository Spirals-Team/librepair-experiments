package ru.job4j.automat;

import ru.job4j.automat.input.ConsoleInput;
import ru.job4j.automat.input.Input;
import ru.job4j.automat.menu.MenuAutoman;

public class StartUI {
    private Input input;
    private Automat automat;

    private StartUI(Input input, Automat automat) {
        this.input = input;
        this.automat = automat;
    }

    private void init() {
        MenuAutoman menuAutoman = new MenuAutoman(input, automat);
        menuAutoman.fillActions();
        do {
            menuAutoman.show();
            menuAutoman.select(Integer.valueOf(input.ask("Select: ")));
        } while (!"y".equals(input.ask("Exit? (y/n): ")));
    }

    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Automat()).init();
    }
}
