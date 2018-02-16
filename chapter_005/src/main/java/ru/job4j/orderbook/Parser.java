package ru.job4j.orderbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private Map<String, Map<Integer, Order>> books;

    public Parser(Map<String, Map<Integer, Order>> books) {
        this.books = books;
    }

    // parse file
    public void parse() {
        try (BufferedReader br = new BufferedReader(new FileReader("orders.xml"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("<AddOrder")) {
                    Order order = createOrder(line);

                    Map<Integer, Order> orders = books.computeIfAbsent(order.getBook(), key -> new HashMap<>());
                    orders.put(order.getId(), order);

                } else if (line.startsWith("<DeleteOrder")) {
                    String book = findValue(line, "book");
                    Integer id = Integer.parseInt(findValue(line, "orderId"));
                    books.get(book).remove(id);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // create new order
    private Order createOrder(String line) {
        return  new Order(
                findValue(line, "book"),
                findValue(line, "operation"),
                Float.parseFloat(findValue(line, "price")),
                Integer.parseInt(findValue(line, "volume")),
                Integer.parseInt(findValue(line, "orderId"))
        );
    }

    // find value in string
    private String findValue(String line, String attribute) {
        int index = line.indexOf(attribute) + attribute.length();
        boolean flag = false;
        int i = 0;
        while (!line.isEmpty()) {
            if (line.charAt(index) == '\"') {
                if (flag) {
                    return line.substring(i, index);
                }
                flag = true;
                i = index + 1;
            }
            index++;
        }
        return line;
    }
}
