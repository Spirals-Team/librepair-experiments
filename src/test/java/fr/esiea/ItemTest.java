package fr.esiea;

import fr.esiea.models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class ItemTest {
    @Test
    public void setters() {
    	int testId = 1;
		String testName = "testName";
		int testSellIn = 2;
		int testQuality = 3;
		int testPrice = 4;
		int testQuantity = 5;
        Item item = new Item();
        SoftAssertions solftly = new SoftAssertions();

        item.setId(testId);
        solftly.assertThat(item.getId())
            .as("foo id")
            .isEqualTo(testId);

        item.setName(testName);
        solftly.assertThat(item.getName())
            .as("foo name")
            .isEqualTo(testName);

        item.setSellIn(testSellIn);
        solftly.assertThat(item.getSellIn())
                .as("foo sellIn")
                .isEqualTo(testSellIn);

        item.setQuality(testQuality);
        solftly.assertThat(item.getQuality())
            .as("foo quality")
            .isEqualTo(testQuality);
        
        item.setPrice(testPrice);
        solftly.assertThat(item.getPrice())
	        .as("foo price")
	        .isEqualTo(testPrice);

        item.setQuantity(testQuantity);
        solftly.assertThat(item.getQuantity())
                .as("foo quantity")
                .isEqualTo(testQuantity);
        
        solftly.assertAll();
    }
    
    @Test
    public void display(){
		Item item = new Item("testName",1,2,3,4);
        SoftAssertions solftly = new SoftAssertions();
        
        solftly.assertThat(item.toString())
	        .as("foo toString")
	        .isEqualTo("testName, sellIn : 1, quality : 2, price : 3, quantity : 4");
        
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

}
