package ru.job4j.stock;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Тесты для биржи
 */
public class StockTest {

    Stock stock = new Stock();

    @Before
    public void initValue() {
        stock.addBook("firm1");
        stock.addBook("firm2");
        stock.addBook("firm3");
        stock.addBook("firm4");
        stock.addBook("firm5");
        stock.getGlassByBook("firm4").action(new Bid("firm4", Bid.Type.ADD, Bid.Action.ASK, 13, 10));
        stock.getGlassByBook("firm4").action(new Bid("firm4", Bid.Type.ADD, Bid.Action.BID, 16, 5));
    }

    @Test
    /**
     * Тест проверяет добавление эммитента на биржу
     * Сначала проверяем что эммитента нет.. потом его добавляем.. и проверяем что он есть.
     */
    public void testAddBook() {
        assertThat(stock.getGlassByBook("firm10"), is(nullValue()));
        stock.addBook("firm10");
        assertThat(stock.getGlassByBook("firm10"), is(notNullValue()));
    }

    @Test
    /**
     * Тест проверяет добавление добавление заявки в стакан и ее удаление
     * Проверяем что заявки нет в стакане.. добавляем заявку в стакан.. проверяем что она есть.. дабавляем заявку
     * на удаление предыдущей заявки.. проверяем что она удалилась
     */
    public void testAddDeleteBid() {
        assertThat(stock.getGlassByBook("firm1").findByBid(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 15, 20)), is(nullValue()));
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 15, 20));
        assertThat(stock.getGlassByBook("firm1").findByBid(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 15, 20)), is(notNullValue()));
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.DELETE, Bid.Action.BID, 15, 20));
        assertThat(stock.getGlassByBook("firm1").findByBid(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 15, 20)), is(nullValue()));
    }

    @Test
    /**
     * Тест проверят систему выполнения заявок
     * Проверяем наличие заявки, которая должна быть выполнена результате торгов.. проводим торги..
     * Проверяем что завка ушла..
     */
    public void testShakeGlass() {
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 17, 25));
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 35, 20));
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 11, 20));
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.ADD, Bid.Action.BID, 35, 14));
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.ADD, Bid.Action.ASK, 11, 15));
        stock.getGlassByBook("firm1").action(new Bid("firm1", Bid.Type.ADD, Bid.Action.ASK, 20, 15));
        assertThat(stock.getGlassByBook("firm1").findByBid(new Bid("firm1", Bid.Type.ADD, Bid.Action.ASK, 20, 15)), is(notNullValue()));
        System.out.println(" Стакан до торгов:");
        System.out.println(stock.getGlassByBook("firm1"));
        stock.getGlassByBook("firm1").shakeGlass();
        assertThat(stock.getGlassByBook("firm1").findByBid(new Bid("firm1", Bid.Type.ADD, Bid.Action.ASK, 20, 15)), is(nullValue()));
        System.out.println(" Стакан после торгов:");
        System.out.println(stock.getGlassByBook("firm1"));
    }

}
