package ru.job4j.collections.orderbook;

import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Order book.
 * @author Hincu Andrei (andreih1981@gmail.com)on 23.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class OrderBook {
    /**
     * Хранилище книг.
     */
    private Map<String, TreeMap<Integer, Order>> orders = new HashMap<>();

    /**
     *Метод осуществляет парсинг файла.
     */
    private void parseXml() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        Handler handler = new Handler(this);
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
            saxParser.parse(new File("orders.xml"), handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Метод добовляет или удаляет заявки из хранилища по мере парсинга файла.
     * @param order заявка.
     * @param flag флаг true - добавить заявку. false - удалить заявку из хранилища.
     */
    public void addOrRemove(Order order, boolean flag) {
        orders.putIfAbsent(order.getBook(), new TreeMap<Integer, Order>());
        if (flag) {
            TreeMap<Integer, Order> map = orders.get(order.getBook());
            map.put(order.getId(), order);
        } else {
            orders.get(order.getBook()).remove(order.getId());
        }
    }

    /**
     * Глвавный метод запускает парсинг, суммирование и агрегацию встречных заявок.
     */
    public void start() {
        this.parseXml();
        final String buyOperation = "BUY";
        for (HashMap.Entry<String, TreeMap<Integer, Order>> map : orders.entrySet()) {
            String bookName = map.getKey();
            TreeMap<Integer, Order> values = map.getValue();
            TreeSet<Order> buy = new TreeSet<>();
            TreeSet<Order> sell = new TreeSet<>();
            for (Order order : values.values()) {
                // String operation = order.getOperation();
                if (buyOperation.equals(order.getOperation())) {
                    buy = amount(buy, order);
                } else {
                    sell = amount(sell, order);
                }
            }
            this.aggregation(buy, sell);
            this.printOrders(buy, sell, bookName);
        }
    }

    /**
     * Метод формирует вывод бижевого стакана в указанных требованиях.
     * @param buy коллекция с заявками на покупку.
     * @param sell коллекция с заявками на продажу.
     * @param bookName Имя книги заявок.
     */
    public void printOrders(TreeSet<Order> buy, TreeSet<Order> sell, String bookName) {
        final String line = System.lineSeparator();
        final StringBuffer sb = new StringBuffer();
        sb.append(bookName).append(line);
        sb.append("   Bid            ASK").append(line);
        Iterator<Order> buyIterator = buy.iterator();
        Iterator<Order> sellIterator = sell.iterator();
        if (buy.size() > sell.size()) {
            while (sellIterator.hasNext()) {
                sb.append(String.format("%15s %s", buyIterator.next(), sellIterator.next())).append(line);
            }
            while (buyIterator.hasNext()) {
                sb.append(String.format("%15s -----------", buyIterator.next())).append(line);
            }
        } else {
            while (buyIterator.hasNext()) {
                sb.append(String.format("%15s %s", buyIterator.next(), sellIterator.next())).append(line);
            }
            while (sellIterator.hasNext()) {
                sb.append(String.format("--------------- %s", sellIterator.next())).append(line);
            }
        }
        System.out.println(sb);
    }

    /**
     * Метод производит агрегацию встречных заявок.
     * @param buy сет с заявками на покупку.
     * @param sell сет с заявками на продажу.
     */
    public void aggregation(TreeSet<Order> buy, TreeSet<Order> sell) {
        Iterator<Order> iteratorBuy = buy.iterator();
        while (iteratorBuy.hasNext()) {
            final Order orderBuy = iteratorBuy.next();
            for (Iterator<Order> iteratorSell = sell.iterator(); iteratorSell.hasNext();) {
                final Order orderSell = iteratorSell.next();
                final float prise = orderBuy.getPrice() - orderSell.getPrice();
                final int volume = orderBuy.getVolume() - orderSell.getVolume();
                if (prise < 0) {
                    break;
                }
                if (prise >= 0) {
                    if (volume > 0) {
                        iteratorSell.remove();
                        orderBuy.setVolume(volume);
                    }
                    if (volume < 0) {
                        iteratorBuy.remove();
                        orderSell.setVolume(Math.abs(volume));
                        break;
                    }
                    if (volume == 0) {
                        iteratorBuy.remove();
                        iteratorSell.remove();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Метод производитт суммирование обьема заявок с одинаковой ценой.
     * @param set сет однотипных заявок
     * @param order заявка.
     * @return отсортированный сет с уникальными ценами.
     */
    public TreeSet<Order> amount(TreeSet<Order> set, Order order) {
        if (set.isEmpty()) {
            set.add(order);
        } else {
            boolean found = false;
            for (Order o : set) {
                if (o.getPrice() == order.getPrice()) {
                    o.setVolume(o.getVolume() + order.getVolume());
                    found = true;
                    break;
                }
            }
            if (!found) {
                set.add(order);
            }
        }
        return set;
    }
}