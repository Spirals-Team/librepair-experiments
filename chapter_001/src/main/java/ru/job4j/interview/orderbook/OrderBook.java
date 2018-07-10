package ru.job4j.interview.orderbook;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import static ru.job4j.interview.orderbook.Operation.*;

/**
 * Order book.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class OrderBook {

    private final Book book = new Book();
    private String name;

    OrderBook(String name) {
        this.name = name;
    }

    /**
     * Add order to bid or ask.
     *
     * @param order order
     */
    public void add(final Order order) {
        TreeMap<Double, TreeMap<Integer, Order>> tradeBook =
            book.getTradeBook(revertOperation(order.getOperation()));
        Order updatedOrder =
            tradeIfPossible(order, tradeBook.entrySet().iterator());
        placeOrder(updatedOrder, book.getTradeBook(updatedOrder.getOperation()));
    }

    /**
     * Delete order from bid or ask collection.
     *
     * @param id id with which the specified order is to be associated
     * @return {@code true} if deleting is successfully
     */
    public boolean delete(final int id) {
        return removeOrder(id, book.getTradeBook(BUY))
            || removeOrder(id, book.getTradeBook(SELL));
    }

    private void placeOrder(final Order updatedOrder,
                            final Map<Double, TreeMap<Integer, Order>> collection) {
        if (updatedOrder.getVolume() > 0) {
            TreeMap<Integer, Order> orders = collection.getOrDefault(
                updatedOrder.getPrice(), new TreeMap<>());
            orders.putIfAbsent(updatedOrder.getOrderId(), updatedOrder);
            collection.put(updatedOrder.getPrice(), orders);
        }
    }

    private Order tradeIfPossible(final Order order,
                                  final Iterator<Map.Entry<Double, TreeMap<Integer, Order>>> priceIt) {
        boolean isTradeNecessary = true;
        while (priceIt.hasNext() && order.getVolume() > 0 && isTradeNecessary) {
            Map.Entry<Double, TreeMap<Integer, Order>> entry = priceIt.next();
            Iterator<Map.Entry<Integer, Order>> orderIt =
                entry.getValue().entrySet().iterator();
            while (orderIt.hasNext() && isTradeNecessary) {
                Map.Entry<Integer, Order> record = orderIt.next();
                isTradeNecessary = trade(order, record.getValue());
                if (record.getValue().getVolume() == 0 && isTradeNecessary) {
                    orderIt.remove();
                }
            }
            if (entry.getValue().isEmpty() && isTradeNecessary) {
                priceIt.remove();
            }
        }
        return order;
    }

    private boolean trade(final Order processedOrder, final Order proposedOrder) {
        return processedOrder.getOperation().equals(SELL)
            ? matchOffer(processedOrder, proposedOrder)
            : matchOffer(proposedOrder, processedOrder);
    }

    private boolean matchOffer(final Order incomeOrder, final Order existOrder) {
        boolean isMatched = false;
        if (existOrder.getPrice() >= incomeOrder.getPrice()) {
            swap(incomeOrder, existOrder);
            isMatched = true;
        }
        return isMatched;
    }

    private void swap(final Order order1, final Order order2) {
        int amount = order2.getVolume() - order1.getVolume();
        if (amount > 0) {
            order2.setVolume(amount);
            order1.setVolume(0);
        } else {
            order1.setVolume(-amount);
            order2.setVolume(0);
        }
    }

    private boolean removeOrder(final int id,
                                final Map<Double, TreeMap<Integer, Order>> orders) {
        boolean result = false;
        for (Map.Entry<Double, TreeMap<Integer, Order>> entry : orders.entrySet()) {
            if (entry.getValue().remove(id) != null && entry.getValue().isEmpty()) {
                result = orders.remove(entry.getKey()) != null;
                break;
            }
        }
        return result;
    }

    public TreeMap<Double, TreeMap<Integer, Order>> getBids() {
        return book.getBids();
    }

    public TreeMap<Double, TreeMap<Integer, Order>> getAsks() {
        return book.getAsks();
    }

    public String getName() {
        return name;
    }
}
