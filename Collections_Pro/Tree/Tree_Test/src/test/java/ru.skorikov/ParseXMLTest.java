package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.util.Map;

import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 21.12.17
 * @ version: java_kurs_standart
 */
public class ParseXMLTest {
    /**
     * Проверим самодельный парсинг.
     * В результате должны получить карту с одной картой в которой один ордер.
     * "book-1","SELL","100.00","40".
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenParseFileThenReturnArray() throws Exception {
        Start start = new Start();
        File file = new File("test_book.xml");
        start.setFile(file);
        start.getOrderBook().work(start.getFile());

        Map<String, Map<Integer, Order>> mapMap = start.getOrderBook().getBooks();

        Assert.assertEquals(mapMap.size(), 1);

        Order order = mapMap.get("book-1").get(0);

        Assert.assertThat(order.getOperation(), is("SELL"));
        Assert.assertThat(order.getPrice(), is(100.00));
        Assert.assertThat(order.getVolume(), is(40));

    }

}