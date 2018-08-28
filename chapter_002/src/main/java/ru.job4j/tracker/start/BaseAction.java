package ru.job4j.tracker.start;

/**
 * Абстрактный класс BaseAction унаследованный от интерфейса UserAction.
 */
public abstract class BaseAction implements UserAction {
    /**
     * Переменная для названия меню.
     */
    private String menuName;
    /**
     * Переменная для номера меню.
     */
    private int key;

    /**
     * Конструктор.
     * @param menuName принимаем название меню.
     * @param key принимаем номер меню.
     */
    BaseAction(String menuName, int key) {
        this.menuName = menuName;
        this.key = key;
    }

    /**
     * Реализуем метод key() чтобы дальнейшие наследники не реализовывали.
     * @return вернем номер меню.
     */
    @Override
    public int key() {
        return key;
    }

    /**
     * Реализуем метод info() чтобы дальнейшие наследники не реализовывали.
     * @return вернем информацию.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key(), menuName);
    }
}
