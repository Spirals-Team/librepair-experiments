package fr.esiea;

import fr.esiea.models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class ConjuredItemTest {
    @Test
    public void conjured_item_quality_degrades_twice_more_than_normal_item() {
        Item conjuredItem = new ConjuredItem(new Item("Conjured", 1, 10));
        Item[] items = new Item[]{conjuredItem};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(conjuredItem.getQuality())
            .as("conjuredItem quality")
            .isEqualTo(8);

        solftly.assertThat(conjuredItem.getSellIn())
            .as("conjuredItem sellIn")
            .isEqualTo(0);

        solftly.assertAll();
    }

    @Test
    public void conjured_item_default() {
        Item conjuredItem = new ConjuredItem(new Item("foo", 0, 0));

        SoftAssertions solftly = new SoftAssertions();
        conjuredItem.setName("bar");
        solftly.assertThat(conjuredItem.getName())
            .as("conjuredItem name")
            .isEqualTo("bar");

        conjuredItem.setQuality(1);
        solftly.assertThat(conjuredItem.getQuality())
            .as("conjuredItem quality")
            .isEqualTo(1);

        conjuredItem.setSellIn(1);
        solftly.assertThat(conjuredItem.getSellIn())
            .as("conjuredItem sellIn")
            .isEqualTo(1);

        solftly.assertThat(conjuredItem.toString())
            .as("conjuredItem toString")
            .isEqualTo("bar, sellIn : 1, quality : 1, price : 0, quantity : 0");

        solftly.assertAll();
    }
}
