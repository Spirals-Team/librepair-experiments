package fr.esiea.models;

public class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {

            /**
             * Update Quality
             */
            items[i].updateQuality();

            /**
             * The Quality of an item is never more than 50
             * The Quality of a legendary item can never be altered.
             */
            if (items[i].getQuality() > 50 && !(items[i] instanceof LegendaryItem)) {
                items[i].setQuality(50);
            }

            /**
             * The Quality of an item is never negative
             */
            if (items[i].getQuality() < 0) {
                items[i].setQuality(0);
            }

            /**
             * Decrease SellIn
             */
            if (!(items[i] instanceof LegendaryItem)) {
                items[i].setSellIn(items[i].getSellIn() - 1);
            }
        }
    }
}
