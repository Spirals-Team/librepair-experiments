package ru.job4j.orderbook;

import java.util.*;

public class OrdersCalculator {
    private Map<String, Map<Integer, Order>> books;
    private Map<String, Map<String, List<Order>>> result;

    public OrdersCalculator(Map<String, Map<Integer, Order>> books, Map<String, Map<String, List<Order>>> result) {
        this.books = books;
        this.result = result;
    }

    public void calculate() {
        for (Map<Integer, Order> orders : books.values()) {
            split(orders);
        }
    }

    // split orders by buy and sell
    private void split(Map<Integer, Order> orders) {
        Map<Float, Order> buy = new TreeMap<>(Comparator.reverseOrder());
        Map<Float, Order> sell = new TreeMap<>();

        for (Order order : orders.values()) {
            if ("BUY".equals(order.getOperation())) {
                addOrder(buy, order);
            } else if ("SELL".equals(order.getOperation())) {
                addOrder(sell, order);
            }
        }
        match(new LinkedList<>(buy.values()), new LinkedList<>(sell.values()));
    }

    // add order
    private void addOrder(Map<Float, Order> map, Order order) {
        Order exist = map.get(order.getPrice());
        if (exist != null) {
            map.put(exist.getPrice(), new Order(
                    order.getBook(),
                    order.getOperation(),
                    order.getPrice(),
                    order.getVolume() + exist.getVolume(),
                    order.getId()
            ));
        } else {
            map.put(order.getPrice(), order);
        }
    }

    // matching orders
    private void match(List<Order> buy, List<Order> sell) {
        int buyIndex = 0;
        int sellIndex = 0;
        Order buyOrder;
        Order sellOrder;

        while (buy.size() > buyIndex && sell.size() > sellIndex) {
            buyOrder = buy.get(buyIndex);
            sellOrder = sell.get(sellIndex);
            if (buyOrder.getPrice() >= sellOrder.getPrice()) {
                if (buyOrder.getVolume() > sellOrder.getVolume()) {
                    buy.set(buyIndex, new Order(
                            buyOrder.getBook(),
                            buyOrder.getOperation(),
                            buyOrder.getPrice(),
                            buyOrder.getVolume() - sellOrder.getVolume(),
                            buyOrder.getId()
                    ));
                    sell.remove(sellOrder);
                } else if (buyOrder.getVolume() < sellOrder.getVolume()) {
                    sell.set(sellIndex, new Order(
                            sellOrder.getBook(),
                            sellOrder.getOperation(),
                            sellOrder.getPrice(),
                            sellOrder.getVolume() - buyOrder.getVolume(),
                            sellOrder.getId()
                    ));
                    buy.remove(buyOrder);
                } else {
                    buy.remove(buyOrder);
                    sell.remove(sellOrder);
                }
            } else {
                buyIndex++;
                sellIndex++;
            }
        }
        fillResult(buy, sell);
        showOrders(buy, sell);
    }

    // fill result map to orders
    private void fillResult(List<Order> buy, List<Order> sell) {
        fill(buy);
        fill(sell);
    }

    private void fill(List<Order> transaction) {
        for (Order order : transaction) {
            result.computeIfAbsent(order.getBook(), key -> new HashMap<>())
                    .computeIfAbsent(order.getOperation(), key -> new ArrayList<>())
                    .add(order);
        }
    }

    // show buy and sell orders
    private void showOrders(List<Order> buy, List<Order> sell) {
        int size = buy.size() > sell.size() ? buy.size() : sell.size();
        StringBuilder sb = new StringBuilder();
        sb.append("===================\n  BID        ASK\n");
        for (int i = 0; i < size; i++) {
            if (i < buy.size()) {
                sb.append(String.format("%s%s%s", buy.get(i).getVolume(), "@", buy.get(i).getPrice()));
            } else {
                sb.append("-------");
            }
            sb.append(" - ");
            if (i < sell.size()) {
                sb.append(String.format("%s%s%s", sell.get(i).getVolume(), "@", sell.get(i).getPrice()));
            } else {
                sb.append("-------");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
