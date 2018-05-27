package ru.job4j.coffeemachine;

public class NotEnoughMoney extends RuntimeException {
    public NotEnoughMoney(String msg) {
        super(msg);
    }
}
