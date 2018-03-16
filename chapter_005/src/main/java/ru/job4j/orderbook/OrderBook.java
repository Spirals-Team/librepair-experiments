package ru.job4j.orderbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBook {
    private Map<String, Map<Integer, Order>> orderBooks = new HashMap<>();
    private Map<String, Map<String, List<Order>>> result = new HashMap<>();

    public Map<String, Map<String, List<Order>>> getResult() {
        return result;
    }

    public void execute() {
        SAXPars parser = new SAXPars(orderBooks);
        parser.parse();
        OrdersCalculator ordersCalculator = new OrdersCalculator(orderBooks, result);
        ordersCalculator.calculate();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        OrderBook ob = new OrderBook();
        ob.execute();

        System.out.println((System.currentTimeMillis() - start) / 1000 + " sec");
    }
}
