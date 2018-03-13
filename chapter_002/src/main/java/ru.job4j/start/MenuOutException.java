package ru.job4j.start;

/**
 * Создать класс MenuOutException, наследующую RuntimeException который должен принимать сообщение
 * об ошибке в конструктор и передавать его в конструктор родителя.
 */
public class MenuOutException extends RuntimeException {
//public class MenuOutException extends Exception {

    public MenuOutException(String msg) {
        super(msg);
    }
}
