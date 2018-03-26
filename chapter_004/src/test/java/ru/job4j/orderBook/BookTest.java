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
}