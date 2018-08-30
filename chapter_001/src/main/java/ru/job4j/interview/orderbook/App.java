package ru.job4j.interview.orderbook;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

/**
 * App start point for application.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class App {

    private static Logger log = Logger.getLogger(OrderParser.class.getName());

    private final Printer printer = new Printer();
    private final OrderParser parser = new OrderParser();

    public void run(final File xml) {
        TreeMap<String, OrderBook> orderBooks = new TreeMap<>();
        try (InputStream in = new FileInputStream(xml)) {
            parser.init(in);
            long start = currentTimeMillis();
            parser.parse(orderBooks);
            long delay = currentTimeMillis() - start;
            log.info(format("total time: {%s}ms", delay));
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        print(orderBooks);
    }

    private void print(TreeMap<String, OrderBook> orderBooks) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, OrderBook> orderBook : orderBooks.entrySet()) {
            sb.append(printer.printBook(orderBook.getValue())).append("\n");
        }
        System.out.println(sb.toString());
    }
}
