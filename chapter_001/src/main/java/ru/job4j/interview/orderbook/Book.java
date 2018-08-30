package ru.job4j.interview.orderbook;

import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import static ru.job4j.interview.orderbook.Operation.BUY;
import static ru.job4j.interview.orderbook.Operation.SELL;

/**
 * Book that contains bid and ask collections.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Book {

    private HashMap<Operation, TreeMap<Double, TreeMap<Integer, Order>>> book =
        new HashMap<Operation, TreeMap<Double, TreeMap<Integer, Order>>>() {
            {
                put(BUY, new TreeMap<>(Collections.reverseOrder()));
                put(SELL, new TreeMap<>());
            }
        };

    public Book() {
    }

    public TreeMap<Double, TreeMap<Integer, Order>> getBids() {
        return book.get(BUY);
    }

    public TreeMap<Double, TreeMap<Integer, Order>> getAsks() {
        return book.get(SELL);
    }

    public TreeMap<Double, TreeMap<Integer, Order>> getTradeBook(
        final Operation operation) {
        return book.get(operation);
    }
}