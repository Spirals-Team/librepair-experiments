package ru.job4j.collections.orderbook;

import org.junit.Before;
import org.junit.Test;
import java.util.TreeSet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 23.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class OrderBookTest {
    /**
     * хранилище.
     */
    private OrderBook orderBook;

    /**
     * Инициализация.
     */
    @Before
    public void start() {
        orderBook = new OrderBook();
    }

    /**
     *Метод проверяет агрегацию ордеров.
     *Обьемы вычитаются т.к заявки сочетаемы.
     */
    @Test
    public void whenAdTwoOrdersThenVolumesSubtracted() {
        TreeSet<Order> sell = new TreeSet<>();
        TreeSet<Order> buy = new TreeSet<>();
        Order order = new Order("BUY", 100.10f, 100);
        Order order1 = new Order("SELL", 100.0f, 75);
        buy.add(order);
        sell.add(order1);
        orderBook.aggregation(buy, sell);
        assertThat(order.getVolume(), is(25));
    }

    /**
     * Тест проверяет метод amount который
     * складывает обьемы при равных значениях цены.
     */
    @Test
    public void whenWasAdTwoOrdersWithEqualsPrice() {
        TreeSet<Order> sell = new TreeSet<>();
        Order order2 = new Order("SELL", 100.10f, 50);
        Order order3 = new Order("SELL", 100.10f, 50);
        System.out.println(order2.equals(order3));
        sell.add(order2);
        orderBook.amount(sell, order3);
        Order order = new Order("SELL", 100.10f,  100);
        assertThat(order2, is(order));
    }

    /**
     * Метод запускает всю программу и выводит в консоль результат.
     */
    @Test
    public void startProgram() {
        orderBook.start();
    }
}