package ru.job4j.automat.menu;

import ru.job4j.automat.Automat;
import ru.job4j.automat.input.Input;

/**
 * Class ShowAllCoins.
 */
public class ShowAllCoins extends BaseAction {

    /**
     * Конструктор.
     *
     * @param menuName принимаем название меню.
     * @param key      принимаем номер меню.
     */
    ShowAllCoins(String menuName, int key) {
        super(menuName, key);
    }

    @Override
    public void execute(Input input, Automat automat) {
        automat.insertedMoney();
        System.out.println("Вы внесли " + automat.getSummaInput() + " рублей.");
    }
}
