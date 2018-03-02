package fr.esiea;

import fr.esiea.models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void non_specific_item_default() {
        Item item = new Item();
        SoftAssertions solftly = new SoftAssertions();

        solftly.assertThat(item.getName())
            .as("foo name")
            .isEqualTo("");

        item.setName("foo");
        solftly.assertThat(item.getName())
            .as("foo name")
            .isEqualTo("foo");

        item.setPrice(10);
        solftly.assertThat(item.getPrice())
                .as("foo price")
                .isEqualTo(10);

        item.setQuantity(2);
        solftly.assertThat(item.getQuantity())
                .as("foo quantity")
                .isEqualTo(2);

        solftly.assertThat(item.getQuality())
            .as("foo quality")
            .isEqualTo(0);

        solftly.assertThat(item.getSellIn())
            .as("foo sellIn")
            .isEqualTo(0);

        solftly.assertThat(item.toString())
            .as("foo toString")
            .isEqualTo("foo, sellIn : 0, quality : 0, price : 10, quantity : 2");


        solftly.assertAll();
    }

    @Test
    public void non_specific_item_quality_and_sellin_decrease_by_one() {
        Item item = new Item("foo", 1, 1);
        Item[] items = new Item[]{item};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(item.getQuality())
            .as("foo quality")
            .isEqualTo(0);

        solftly.assertThat(item.getSellIn())
            .as("foo sellIn")
            .isEqualTo(0);

        solftly.assertAll();
    }

    @Test
    public void non_specific_item_quality_is_never_negative() {
        Item item = new Item("foo", 2, 2);
        Item[] items = new Item[]{item};

        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();
        tavern.updateQuality();
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(item.getQuality())
            .as("foo quality")
            .isEqualTo(0);

        solftly.assertThat(item.getSellIn())
            .as("foo sellIn")
            .isEqualTo(-1);

        solftly.assertAll();
    }

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


    @Test
    public void legendary_item_never_has_to_be_sold_or_decreases_in_quality() {
        Item legendaryItem = new LegendaryItem(new Item("Sulfuras, Hand of Ragnaros", -1, 80));
        Item[] items = new Item[]{legendaryItem};
        GildedRose tavern = new GildedRose(items);
        tavern.updateQuality();

        SoftAssertions solftly = new SoftAssertions();
        solftly.assertThat(legendaryItem.getName())
            .as("legendaryItem name")
            .isEqualTo("Sulfuras, Hand of Ragnaros");

        solftly.assertThat(legendaryItem.getQuality())
            .as("legendaryItem quality")
            .isEqualTo(80);

        solftly.assertThat(legendaryItem.getSellIn())
            .as("legendaryItem sellIn")
            .isEqualTo(-1);

        solftly.assertThat(legendaryItem.toString())
            .as("legendaryItem toString")
            .isEqualTo("Sulfuras, Hand of Ragnaros, -1, 80");

        solftly.assertAll();
    }

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
            .isEqualTo("bar, 0, 80");

        solftly.assertAll();
    }

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
            .isEqualTo("bar, 1, 1");

        solftly.assertAll();
    }

}
