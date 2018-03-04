package fr.esiea;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import fr.esiea.models.CheeseItem;
import fr.esiea.models.GildedRose;
import fr.esiea.models.Item;

public class CheeseItemTest {
    @Test
    public void cheese_item_quality_increase_for_each_day_past_sell_date() {
        Item cheeseItem = new CheeseItem("Aged Brie", -1, 1);
        Item[] items = new Item[]{cheeseItem};
        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(cheeseItem.getQuality())
            .as("cheeseItem quality")
            .isEqualTo(3);

        solftly.assertThat(cheeseItem.getSellIn())
            .as("cheeseItem sellIn")
            .isEqualTo(-2);

        solftly.assertAll();
    }
}
