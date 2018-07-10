package ru.job4j.interview.orderbook;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Printer.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Printer {

    /**
     * Print books aggregated by price.
     *
     * @return string representing the books
     */
    public String printBook(final OrderBook orderBook) {
        StringBuilder sb = createTitle(orderBook);
        TreeMap<Double, TreeMap<Integer, Order>> asks = orderBook.getAsks();
        TreeMap<Double, TreeMap<Integer, Order>> bids = orderBook.getBids();
        Iterator<Map.Entry<Double, TreeMap<Integer, Order>>> asksIt =
            asks.entrySet().iterator();
        Iterator<Map.Entry<Double, TreeMap<Integer, Order>>> bidsIt =
            bids.entrySet().iterator();
        while (bidsIt.hasNext() || asksIt.hasNext()) {
            sb.append(formatPrint(aggregate(bidsIt)));
            sb.append(" \t  –  \t ");
            sb.append(formatPrint(aggregate(asksIt)));
            sb.append("\n");
        }
        return sb.toString();
    }

    private StringBuilder createTitle(OrderBook orderBook) {
        StringBuilder sb = new StringBuilder(orderBook.getName() + "\n");
        sb.append("\tBID \t\t\t ASK\n");
        sb.append("Volume@Price  –  Volume@Price\n");
        return sb;
    }

    private Order aggregate(
        final Iterator<Map.Entry<Double, TreeMap<Integer, Order>>> it) {
        Order order = null;
        if (it.hasNext()) {
            Map.Entry<Double, TreeMap<Integer, Order>> orders = it.next();
            order = aggregateByPrice(orders.getValue());
        }
        return order;
    }

    private String formatPrint(final Order order) {
        return order != null ? order.toStringSimple() : "--------";
    }

    private Order aggregateByPrice(final TreeMap<Integer, Order> collection) {
        Order orderToPrint = new Order();
        int volume = collection.entrySet().stream()
            .mapToInt(order -> order.getValue().getVolume()).sum();
        orderToPrint.setVolume(volume);
        orderToPrint.setPrice(collection.firstEntry().getValue().getPrice());
        return orderToPrint;
    }
}