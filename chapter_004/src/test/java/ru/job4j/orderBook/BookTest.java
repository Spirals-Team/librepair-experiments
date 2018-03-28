package ru.job4j.orderBook;

import org.junit.Test;

public class BookTest {
    @Test
    public void firstTest() {
        Book book = new Book("First");
        Item item1 = new Item("First", "add", "buy", 10, 45);
        Item item2 = new Item("First", "add", "buy", 10, 16);
        book.addItem(item1);
        book.addItem(item2);
        book.print();
    }

    @Test
    public void secondTest() {
        Book book = new Book("Second");
        Item item1 = new Item("Second", "add", "buy", 10, 45);
        Item item2 = new Item("Second", "add", "buy", 10, 16);
        Item item3 = new Item("Second", "add", "buy", 10, 9);
        book.addItem(item1);
        book.addItem(item2);
        book.addItem(item3);
        book.print();
    }
}