package fr.esiea;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItemTest {

	@Test
	public void updateSellInTest(){
		Item item = new Item("Item Test",10,10);
		item.updateSellIn();
		assertEquals(9,item.sellIn);
	}

	@Test
  	public void updateQualityAgedBrieTest(){
    	AgedBrieItem agedBrieItem = new AgedBrieItem("Aged Brie",-2,10);
    	agedBrieItem.updateQuality();
    	assertEquals(12, agedBrieItem.quality);
  	}

  	@Test
  	public void updateItemTest(){
    	Item[] items= new Item[] { 
    		new AgedBrieItem("Aged Brie", -5, 10),
     		new BackstagePassesItem("Backstage passes to a TAFKAL80ETC concert",10,20),
    		new BackstagePassesItem("Backstage passes to a TAFKAL80ETC concert",5,10),
     		new BackstagePassesItem("Backstage passes to a TAFKAL80ETC concert",-2,5),
     		new ConjuredItem("Conjured",-4,2)};
     	for(int i=0;i<5;i++)
     		items[i].updateQuality();
     	assertEquals(12, items[0].quality);
    	assertEquals(22, items[1].quality);
   		assertEquals(13, items[2].quality);
    	assertEquals(0, items[3].quality);
    	assertEquals(0, items[4].quality);
    }
    
}