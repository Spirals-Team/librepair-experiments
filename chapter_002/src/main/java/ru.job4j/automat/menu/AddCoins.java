package ru.job4j.automat.menu;

import ru.job4j.automat.Automat;
import ru.job4j.automat.input.Input;

/**
 * Class AddCoins.
 */
public class AddCoins extends BaseAction {
    /**
     * Конструктор.
     *
     * @param menuName принимаем название меню.
     * @param key      принимаем номер меню.
     */
    AddCoins(String menuName, int key) {
        super(menuName, key);
    }
    @Override
    public void execute(Input input, Automat automat) {
        do {
            automat.makeMoney(Integer.valueOf(input.ask("Внесите монеты наминалом (10, 5, 2, 1): ")));
        } while (!"y".equals(input.ask("Закончить внесение (y/n): ")));
    }
}
