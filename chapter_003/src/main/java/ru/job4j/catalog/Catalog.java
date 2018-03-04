package ru.job4j.catalog;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<String> catalog = new ArrayList<>();

    public List<String> getCatalog() {
        return catalog;
    }

    public List<String> sortAscending() {
        this.catalog.sort(String::compareTo);
        return this.catalog;
    }

    public List<String> sortDescending() {
        this.sortAscending().sort((o1, o2) -> o1.compareTo(o2) == 1 ? -1 : o1.compareTo(o2) == -1 ? 1 : 0);
        return this.catalog;
    }
}
