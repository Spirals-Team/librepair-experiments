package me.pagarme.factory;

import java.util.Collection;
import java.util.ArrayList;

import me.pagar.model.Item;

public class ItemFactory {
    public static final String DEFAULT_ID = "r123";
    public static final String DEFAULT_TITLE = "Red pill";
    public static final int DEFAULT_UNIT_PRICE = 500;
    public static final int DEFAULT_QUANTITY = 1;
    public static final Boolean DEFAULT_TANGIBLE = true;

    public Collection<Item> create() {
        Collection<Item> items = new ArrayList<Item>();
        Item item = new Item(DEFAULT_ID, DEFAULT_TITLE, DEFAULT_UNIT_PRICE, DEFAULT_QUANTITY, DEFAULT_TANGIBLE);

        items.add(item);
        return items;
    }
}
