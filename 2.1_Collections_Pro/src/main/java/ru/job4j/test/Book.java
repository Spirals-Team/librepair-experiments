package ru.job4j.test;


import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * Class book.
 */
public class Book {
    /**
     * @param orders collections for all orders/
     */
    private final Collection<Order> orders;

    /**
     * Comparator for sort bid.
     */
    private static final Comparator<Float> ASC = new Comparator<Float>() {
        @Override
        public int compare(Float o1, Float o2) {
            return o1.compareTo(o2);
        }
    };

    /**
     * Comparator for ASK.
     */
    private static final Comparator<Float> DESC = new Comparator<Float>() {
        @Override
        public int compare(Float o1, Float o2) {
            return o2.compareTo(o1);
        }
    };

    /**
     * Constrcutor.
     * @param orders o
     */
    public Book(final Collection<Order> orders) {
        this.orders = orders;
    }

    /**
     * Metgod calculate.
     */
    public void calculate() {
        Map<Float, Order> sell = new TreeMap<Float, Order>(DESC);
        Map<Float, Order> buy = new TreeMap<Float, Order>(ASC);
        for (Order order : orders) {
            this.add(order.type == Order.Type.BUY ? buy : sell, order);
        }
        this.delete(sell, buy);
        this.show(sell, buy);
    }

    /**
     * Method add.
     * @param map map
     * @param order order.
     */
    public void add(final Map<Float, Order> map, Order order) {
        Order find = map.get(order.price);
        if (find != null) {
            map.put(find.price, new Order(find.book, find.type, find.price, find.volume + order.volume, find.id));
        } else {
            map.put(order.price, order);
        }
    }

    /**
     * Method for deleting.
     * @param sell sell
     * @param buy buy
     */
    public void delete(Map<Float, Order> sell, Map<Float, Order> buy) {
        boolean check = true;
        Iterator itSell = sell.values().iterator();
        Order sOrd = (Order) itSell.next();
        Iterator itBuy = buy.values().iterator();
        Order bOrd = (Order) itBuy.next();
        while (check) {
            boolean result = sOrd.price >= bOrd.price;
            if (result) {
                if (sOrd.volume > bOrd.volume) {
                    sOrd.volume = sOrd.volume - bOrd.volume;
                    if (itBuy.hasNext()) {
                        itBuy.remove();
                        bOrd = (Order) itBuy.next();
                    }
                } else if (sOrd.volume == bOrd.volume) {
                    if (itBuy.hasNext()) {
                        itBuy.remove();
                        bOrd = (Order) itBuy.next();
                    }
                    if (itSell.hasNext()) {
                        itSell.remove();
                        sOrd = (Order) itSell.next();
                    }
                } else if (sOrd.volume < bOrd.volume) {
                    bOrd.volume = bOrd.volume - sOrd.volume;
                    if (itSell.hasNext()) {
                        itSell.remove();
                        sOrd = (Order) itSell.next();
                    }
                }
            } else {
                check = false;
            }
        }
    }

    /**
     * Method show.
     * @param sell BID collection
     * @param buy ASK collection
     */
    public void show(Map<Float, Order> sell, Map<Float, Order> buy) {
        int size = sell.size() > buy.size() ? sell.size() : buy.size();
        StringBuilder builder = new StringBuilder();
        builder.append("\t\tBID" + "\t\t" + "\t\t" + "ASK" + "\n");
        Iterator itSell = sell.values().iterator();
        Iterator itBuy = buy.values().iterator();
        for (int i = 0; i < size; i++) {
            if (itSell.hasNext()) {
                Order ord = (Order) itSell.next();
                builder.append(String.format("%7s @ %s", ord.volume, ord.price));
            } else {
                String s = "-----------";
                builder.append(s);
            }
            if (itBuy.hasNext()) {
                Order ord = (Order) itBuy.next();
                builder.append(String.format("%11s @ %s\n", ord.volume, ord.price));
            } else {
                String s = "      -----------\n";
                builder.append(s);
            }
        }
        System.out.println(builder);
    }
}

