package ru.skorikov;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 02.12.17
 * @ version: java_kurs_standart
 */
public class OrderBook {
    /**
     * Книга книг.
     */
    private Map<String, Map<Integer, Order>> books;

    /**
     * Конструктор.
     */
    public OrderBook() {
        this.books = new TreeMap<>();
    }

    /**
     * Получить книгу.
     *
     * @return книга.
     */
    public Map<String, Map<Integer, Order>> getBooks() {
        return books;
    }

    /**
     * Установить книгу.
     *
     * @param books книга.
     */
    public void setBooks(Map<String, Map<Integer, Order>> books) {
        this.books = books;
    }

    /**
     * Cортировка по операции и объемам.
     * сортируем заявки если SELL по убыванию цены и объемов.
     * если BUY по возрастанию цены и объемов.
     *
     * @param map сортируемая карта.
     * @return отсортированная карта.
     */
    private Map<Integer, Order> sortByVolumeForDelete(Map<Integer, Order> map) {
        List<Map.Entry<Integer, Order>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Order>>() {
            @Override
            public int compare(Map.Entry<Integer, Order> o1, Map.Entry<Integer, Order> o2) {
                int isCompare = 0;
                //операции равны
                if (o1.getValue().getOperation().equals(o2.getValue().getOperation())) {
                    if (o1.getValue().getOperation().equals("SELL")) {
                        if (o1.getValue().getPrice() == o2.getValue().getPrice()) {
                            if (o1.getValue().getVolume() >= o2.getValue().getVolume()) {
                                isCompare = -1;
                            } else {
                                isCompare = 1;
                            }
                        } else {
                            if (o1.getValue().getPrice() > o2.getValue().getPrice()) {
                                isCompare = -1;
                            } else {
                                isCompare = 1;
                            }
                        }
                    } else {
                        if (o1.getValue().getPrice() == o2.getValue().getPrice()) {
                            if (o1.getValue().getVolume() >= o2.getValue().getVolume()) {
                                isCompare = 1;
                            } else {
                                isCompare = -1;
                            }
                        } else {
                            if (o1.getValue().getPrice() > o2.getValue().getPrice()) {
                                isCompare = 1;
                            } else {
                                isCompare = -1;
                            }
                        }
                    }
                } else {
                    if (o1.getValue().getOperation().equals("SELL")) {
                        isCompare = -1;
                    } else {
                        isCompare = 1;
                    }
                }
                return isCompare;
            }
        });
        int index = 0;
        Map<Integer, Order> result = new TreeMap<>();
        for (Map.Entry<Integer, Order> entry : list) {
            result.put(index++, entry.getValue());
        }
        return result;
    }

    /**
     * Cвод позиций с одинаковой ценой в одну.
     * Если ордера имеют одинаковую цену - складываем.
     *
     * @param map карта.
     * @return итоговая книга.
     */
    private Map<Integer, Order> addForOne(Map<Integer, Order> map) {
        Map<Integer, Order> result = new TreeMap<>();
        List<Map.Entry<Integer, Order>> list = new ArrayList<>(map.entrySet());
        int index = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            String operation = list.get(i).getValue().getOperation();
            Double price = list.get(i).getValue().getPrice();
            Integer volume = list.get(i).getValue().getVolume();
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getValue().getPrice().equals(list.get(j).getValue().getPrice())) {
                    volume += list.get(j).getValue().getVolume();

                } else {
                    i = j - 1;
                    break;
                }
            }
            Order order = new Order(operation, price, volume);
            result.put(index++, order);
        }
        return result;
    }

    /**
     * Сопоставление ордеров.
     * Если ордера имеют одинаковый объем и отличаются операцией и ценой согласно задания.
     * сопоставляем - удаляем их из книги.
     *
     * @param map книга.
     * @return отсортированная книга.
     */
    private Map<Integer, Order> collation(Map<Integer, Order> map) {
        List<Map.Entry<Integer, Order>> list = new ArrayList<>(map.entrySet());
        int j = list.size() - 1;
        for (int i = 0; i < j;) {
            if (!list.get(i).getValue().getOperation().equals(list.get(j).getValue().getOperation())) {
                if (list.get(i).getValue().getVolume().equals(list.get(j).getValue().getVolume())) {
                    if (list.get(i).getValue().getPrice() >= list.get(j).getValue().getPrice()) {
                        list.remove(j);
                        list.remove(i);
                        j = j - 2;
                    } else {
                        i++;
                    }
                } else {
                    if (list.get(i).getValue().getPrice() > list.get(j).getValue().getPrice()) {
                        i++;
                    } else {
                        j--;
                    }
                }
            } else {
                break;
            }
        }
        int index = 0;
        Map<Integer, Order> result = new TreeMap<>();
        for (Map.Entry<Integer, Order> entry : list) {
            result.put(index++, entry.getValue());
        }
        return result;
    }

    /**
     * Сопоставление-удаление, свод в одну таблицу.
     * Сортируем, сопоставляем ордера, сводим в одну книгу.
     *
     * @param file входной файл.
     */
    public void work(File file) {
        // Разбор файла.
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            parser.parse(file, handler);
            books = handler.getBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //пока есть книги
        for (Map.Entry<String, Map<Integer, Order>> pair : books.entrySet()) {
            //получили книгу
            Map<Integer, Order> temp = pair.getValue();
            //сортировка листа для сопоставления.
            temp = sortByVolumeForDelete(temp);
            //сопоставление ордеров
            temp = collation(temp);
            //сводная таблица по ценам
            temp = addForOne(temp);
            pair.setValue(temp);
        }
    }

    /**
     * Печать карты.
     *
     */
    public void printMap() {
        for (Map.Entry<String, Map<Integer, Order>> pair : books.entrySet()) {
            Map<Integer, Order> book = pair.getValue();
            System.out.printf("%20s%n", pair.getKey());
            System.out.printf("%-10s %-10s %s%n",
                    "BUY", "PRICE", "SELL");
            for (Map.Entry<Integer, Order> pov : book.entrySet()) {
                if (pov.getValue().getOperation().equals("SELL")) {
                    System.out.printf("%-10s %-10s %s%n",
                            " ",
                            pov.getValue().getPrice(),
                            pov.getValue().getVolume());
                } else {
                    System.out.printf("%-10s %-10s %n",
                            pov.getValue().getVolume(),
                            pov.getValue().getPrice());
                }
            }
        }
    }
}
