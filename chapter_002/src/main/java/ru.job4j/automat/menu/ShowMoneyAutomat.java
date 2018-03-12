package ru.job4j.automat.menu;

import ru.job4j.automat.Automat;
import ru.job4j.automat.input.Input;

/**
 * Class showMoneyAutomat.
 */
public class ShowMoneyAutomat extends BaseAction {
    /**
     * Конструктор.
     *
     * @param menuName принимаем название меню.
     * @param key      принимаем номер меню.
     */
    ShowMoneyAutomat(String menuName, int key) {
        super(menuName, key);
    }

    @Override
    public void execute(Input input, Automat automat) {
        System.out.println(automat.info());
    }
}
