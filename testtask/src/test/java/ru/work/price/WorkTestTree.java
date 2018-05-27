package ru.work.price;

import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class WorkTestTree {
    Work work = new Work();
    TreeMap<String, List<Price>> prices = new TreeMap<>();

    @Before
    public void setup() {
        Price price = new Price("1", 1, 1,
                new Date(2000, 1, 2, 00, 00, 00),
                new Date(2000, 2, 1, 22, 00, 00),
                10000);
        List<Price> list = new ArrayList<>();
        list.add(price);
        prices.put(price.getProduct_code(), list);
    }

    @Test
    public void whenAddNewPriceNewValue() {
        assertThat(prices.get("1").size(), is(1));
        Price price = new Price("1", 1, 1,
                new Date(2000, 2, 2, 00, 00, 00),
                new Date(2000, 2, 3, 00, 00, 00),
                5000);
        List<Price> newprices = new ArrayList<>();
        newprices.add(price);
        TreeMap<String, List<Price>> treeMap = new TreeMap<>();
        treeMap.put(price.getProduct_code(), newprices);
        prices = work.getNewPrice(prices, treeMap);
        assertThat(prices.get("1").size(), is(2));
    }

    @Test
    public void whenAddNewPriceOldValue() {
        assertThat(prices.get("1").size(), is(1));
        Price price = new Price("1", 1, 1,
                new Date(2000, 2, 1, 00, 00, 00),
                new Date(2000, 2, 25, 00, 00, 00),
                10000);
        List<Price> newprices = new ArrayList<>();
        newprices.add(price);
        TreeMap<String, List<Price>> treeMap = new TreeMap<>();
        treeMap.put(price.getProduct_code(), newprices);
        prices = work.getNewPrice(prices, treeMap);
        assertThat(prices.size(), is(1));
        assertThat(prices.get("1").get(0).getEnd(), is(new Date(2000, 2, 25, 00, 00, 00)));
    }

    @Test
    public void whenAddNewPriceInDate() {
        assertThat(prices.get("1").size(), is(1));
        assertThat(prices.get("1").get(0).getValue(), is(10000L));
        Price price = new Price("1", 1, 1,
                new Date(2000, 1, 1, 00, 00, 00),
                new Date(2000, 3, 1, 00, 00, 00),
                7000);
        List<Price> newprices = new ArrayList<>();
        newprices.add(price);
        TreeMap<String, List<Price>> treeMap = new TreeMap<>();
        treeMap.put(price.getProduct_code(), newprices);
        prices = work.getNewPrice(prices, treeMap);
        assertThat(prices.size(), is(1));
        assertThat(prices.get("1").get(0).getValue(), is(7000L));
    }

    @Test
    public void whenAddTwoNewPriceInDate() {
        assertThat(prices.get("1").size(), is(1));
        assertThat(prices.get("1").get(0).getValue(), is(10000L));
        Price price = new Price("1", 1, 1,
                new Date(2000, 1, 1, 00, 00, 00),
                new Date(2000, 1, 15, 23, 59, 59),
                7000);
        List<Price> newprices = new ArrayList<>();
        newprices.add(price);
        price = new Price("1", 1, 1,
                new Date(2000, 1, 16, 00, 00, 00),
                new Date(2000, 4, 1, 00, 00, 00),
                12000);
        newprices.add(price);
        TreeMap<String, List<Price>> treeMap = new TreeMap<>();
        treeMap.put(price.getProduct_code(), newprices);
        prices = work.getNewPrice(prices, treeMap);
        assertThat(prices.get("1").size(), is(2));
        assertThat(prices.get("1").get(0).getValue(), is(7000L));
        assertThat(prices.get("1").get(1).getValue(), is(12000L));
    }
}
