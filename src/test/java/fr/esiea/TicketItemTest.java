package fr.esiea;

import fr.esiea.models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class TicketItemTest {
    @Test
    public void ticket_item_quality_increment_by_one() {
        Item ticketItem = new TicketItem("Backstage passes to a TAFKAL80ETC concert", 11, 30);
        Item[] items = new Item[]{ticketItem};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(ticketItem.getQuality())
            .as("ticketItem quality")
            .isEqualTo(31);

        solftly.assertThat(ticketItem.getSellIn())
            .as("ticketItem sellIn")
            .isEqualTo(10);

        solftly.assertAll();
    }

    @Test
    public void ticket_item_quality_increment_by_two_when_10_days_or_less() {
        Item ticketItem = new TicketItem("Backstage passes to a TAFKAL80ETC concert", 10, 30);
        Item[] items = new Item[]{ticketItem};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(ticketItem.getQuality())
            .as("ticketItem quality")
            .isEqualTo(32);

        solftly.assertThat(ticketItem.getSellIn())
            .as("ticketItem sellIn")
            .isEqualTo(9);

        solftly.assertAll();
    }

    @Test
    public void ticket_item_quality_increment_by_three_when_5_days_or_less() {
        Item ticketItem = new TicketItem("Backstage passes to a TAFKAL80ETC concert", 5, 30);
        Item[] items = new Item[]{ticketItem};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(ticketItem.getQuality())
            .as("ticketItem quality")
            .isEqualTo(33);

        solftly.assertThat(ticketItem.getSellIn())
            .as("ticketItem sellIn")
            .isEqualTo(4);

        solftly.assertAll();
    }

    @Test
    public void ticket_item_quality_drops_to_0_after_concert() {
        Item ticketItem = new TicketItem("Backstage passes to a TAFKAL80ETC concert", -1, 30);
        Item[] items = new Item[]{ticketItem};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(ticketItem.getQuality())
            .as("ticketItem quality")
            .isEqualTo(0);

        solftly.assertThat(ticketItem.getSellIn())
            .as("ticketItem sellIn")
            .isEqualTo(-2);

        solftly.assertAll();
    }
}
