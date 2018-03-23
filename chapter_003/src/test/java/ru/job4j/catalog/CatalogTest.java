package ru.job4j.catalog;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class CatalogTest {
    @Test
    public void whenListSortAscendingThenListSorted() {
        Catalog catalog = new Catalog();
        List<String> list = new ArrayList<>(Arrays.asList(
                "K2\\SK1\\SSK2",
                "K2",
                "K1\\SK1\\SSK1",
                "K1\\SK1",
                "K1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2\\SK1",
                "K2\\SK1\\SSK1"
        ));
        catalog.getCatalog().addAll(list);
        List<String> expect = new ArrayList<>(Arrays.asList(
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        ));
        assertThat(catalog.sortAscending(), is(expect));
    }
    @Test
    public void whenListSortDescendingThenListSorted() {
        Catalog catalog = new Catalog();
        List<String> list = new ArrayList<>(Arrays.asList(
                "K2\\SK1\\SSK2",
                "K2",
                "K1\\SK1\\SSK1",
                "K1\\SK1",
                "K1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2\\SK1",
                "K2\\SK1\\SSK1"
        ));
        catalog.getCatalog().addAll(list);
        List<String> expect = new ArrayList<>(Arrays.asList(
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        ));
        assertThat(catalog.sortDescending(), is(expect));
    }
}
