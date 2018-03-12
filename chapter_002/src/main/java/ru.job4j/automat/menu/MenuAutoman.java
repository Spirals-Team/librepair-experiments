package ru.job4j.automat.menu;

import ru.job4j.automat.Automat;
import ru.job4j.automat.input.Input;

/**
 * Class MenuAutoman.
 */
public class MenuAutoman {
    /**
     * Переменная типа Input.
     */
    private Input input;
    /**
     * Переменная типа Automat.
     */
    private Automat automat;
    /**
     * Массив UserAction.
     */
    private UserAction[] userActions = new UserAction[5];
    /**
     * Позиция в массиве.
     */
    private int position = 0;

    /**
     * Конструктор MenuAutoman.
     * @param input Принимаем ссылку на объект Input.
     * @param automat Принимаем ссылку на объект Automat.
     */
    public MenuAutoman(Input input, Automat automat) {
        this.input = input;
        this.automat = automat;
    }

    /**
     * Метод заполняет массив пунктами меню.
     */
    public void fillActions() {
        this.userActions[position++] = new AddCoins("Add Coins.", 0);
        this.userActions[position++] = new ShowAllCoins("Show all Coins.", 1);
        this.userActions[position++] = new ShowMoneyAutomat("Show money automat.", 2);
        this.userActions[position++] = new ToBuyCookies("To buy cookies. Цена 18 рублей.", 3);
        this.userActions[position++] = new ToGiveACoin("To give a coins. ", 4);
    }

    /**
     * Метод для отображения меню.
     */
    public void show() {
        for (UserAction action : this.userActions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Метод выбора пункта меню.
     * @param key принимаем номер меню.
     */
    public void select(int key) {
        this.userActions[key].execute(this.input, this.automat);
    }
}
