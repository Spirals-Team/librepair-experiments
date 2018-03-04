package fr.esiea;

import fr.esiea.models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.time.Clock;

public class GildedRoseTest {
    @Test
    public void non_legendary_item_quality_is_never_more_than_50() {
        Item cheeseItem = new CheeseItem("Aged Brie", -1, 50);
        Item ticketItem = new TicketItem("Backstage passes to a TAFKAL80ETC concert", 3, 49);
        Item[] items = new Item[]{cheeseItem, ticketItem};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(cheeseItem.getQuality())
            .as("cheeseItem quality")
            .isEqualTo(50);

        solftly.assertThat(cheeseItem.getSellIn())
            .as("cheeseItem sellIn")
            .isEqualTo(-2);

        solftly.assertThat(ticketItem.getQuality())
            .as("ticketItem quality")
            .isEqualTo(50);

        solftly.assertThat(ticketItem.getSellIn())
            .as("ticketItem sellIn")
            .isEqualTo(2);

        solftly.assertAll();
    }

    @Test
    public void update_items(){
        GildedRose tavern = new GildedRose(null);

        Item cheeseItem = new CheeseItem("Aged Brie", -1, 50);
        Item ticketItem = new TicketItem("Backstage passes to a TAFKAL80ETC concert", 3, 49);
        Item[] items = new Item[]{cheeseItem, ticketItem};
        tavern.setItems(items);

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(tavern.getItems())
                .as("foo items")
                .isEqualTo(items);
    }
}
