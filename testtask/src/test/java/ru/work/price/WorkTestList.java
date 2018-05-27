package ru.work.price;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class WorkTestList {
    Work work = new Work();
    List<Price> prices = new ArrayList<>();

    @Before
    public void setup() {
        Price price = new Price("1", 1, 1,
                new Date(2000, 1, 2, 00, 00, 00),
                new Date(2000, 2, 1, 22, 00, 00),
                10000);
        prices.add(price);
    }

    @Test
    public void whenAddNewPriceNewValue() {
        assertThat(prices.size(), is(1));
        Price price = new Price("1", 1, 1,
                new Date(2000, 2, 2, 00, 00, 00),
                new Date(2000, 2, 3, 00, 00, 00),
                5000);
        List<Price> newprices = new ArrayList<>();
        newprices.add(price);
        prices = (ArrayList<Price>) work.getNewPrice(prices, newprices);
        assertThat(prices.size(), is(2));
    }

    @Test
    public void whenAddNewPriceOldValue() {
        assertThat(prices.size(), is(1));
        Price price = new Price("1", 1, 1,
                new Date(2000, 2, 1, 00, 00, 00),
                new Date(2000, 2, 25, 00, 00, 00),
                10000);
        List<Price> newprices = new ArrayList<>();
        newprices.add(price);
        prices = (ArrayList<Price>) work.getNewPrice(prices, newprices);
        assertThat(prices.size(), is(1));
        assertThat(prices.get(0).getEnd(), is(new Date(2000, 2, 25, 00, 00, 00)));
    }

    @Test
    public void whenAddNewPriceInDate() {
        assertThat(prices.size(), is(1));
        assertThat(prices.get(0).getValue(), is(10000L));
        Price price = new Price("1", 1, 1,
                new Date(2000, 1, 1, 00, 00, 00),
                new Date(2000, 3, 1, 00, 00, 00),
                7000);
        List<Price> newprices = new ArrayList<>();
        newprices.add(price);
        prices = (ArrayList<Price>) work.getNewPrice(prices, newprices);
        assertThat(prices.size(), is(1));
        assertThat(prices.get(0).getValue(), is(7000L));
    }

    @Test
    public void whenAddTwoNewPriceInDate() {
        assertThat(prices.size(), is(1));
        assertThat(prices.get(0).getValue(), is(10000L));
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
        prices = (ArrayList<Price>) work.getNewPrice(prices, newprices);
        assertThat(prices.size(), is(2));
        assertThat(prices.get(0).getValue(), is(7000L));
        assertThat(prices.get(1).getValue(), is(12000L));
    }
}
