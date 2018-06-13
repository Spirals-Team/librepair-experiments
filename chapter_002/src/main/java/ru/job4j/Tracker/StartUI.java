package ru.job4j.tracker;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */
public class StartUI {
    private final Input input;
    private final Tracker tracker;

    /**
     * Конструктор, инициализирующий поля
     * @param input пользовательский ввод
     * @param tracker хранилище заявок
     */
    StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * функция для запуска основоного цикла
     */
    public void init() {
        MenuTracker menu = new MenuTracker(input, tracker);
        int rangeValue = menu.fillAction();
        int[] range = new int[rangeValue];
        fillRange(range);
        int key;
        do {
            menu.show();
            key = input.ask("Выбор. ", range);
            menu.select(key);
        } while (key != 6);
    }

    /**
     * функция, заполняющая массив допустимых значения для ввода
     * @param range массив для заполнения
     */
    private void fillRange(int[] range) {
        for (int value = 0; value < range.length; value++) {
            range[value] = value;
        }
    }

    /**
     * Запуск программы
     * @param args аргументы командной строки6
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }
}
