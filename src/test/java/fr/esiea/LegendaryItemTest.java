package fr.esiea;

import fr.esiea.models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class LegendaryItemTest {
    @Test
    public void legendary_item_default() {
        Item legendaryItem = new LegendaryItem(new Item("foo", 0, 80));

        SoftAssertions solftly = new SoftAssertions();
        legendaryItem.setName("bar");
        solftly.assertThat(legendaryItem.getName())
            .as("legendaryItem name")
            .isEqualTo("bar");

        solftly.assertThat(legendaryItem.toString())
            .as("legendaryItem toString")
            .isEqualTo("bar, sellIn : 0, quality : 80, price : 0, quantity : 0");

        solftly.assertAll();
    }
    
    @Test
    public void legendary_item_never_has_to_be_sold_or_decreases_in_quality() {
        Item legendaryItem = new LegendaryItem(new Item("Sulfuras", -1, 80));
        Item[] items = new Item[]{legendaryItem};
        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(legendaryItem.getName())
            .as("legendaryItem name")
            .isEqualTo("Sulfuras");

        solftly.assertThat(legendaryItem.getQuality())
            .as("legendaryItem quality")
            .isEqualTo(80);

        solftly.assertThat(legendaryItem.getSellIn())
            .as("legendaryItem sellIn")
            .isEqualTo(-1);

        solftly.assertThat(legendaryItem.toString())
            .as("legendaryItem toString")
            .isEqualTo("Sulfuras, sellIn : -1, quality : 80, price : 0, quantity : 0");

        solftly.assertAll();
    }
}
