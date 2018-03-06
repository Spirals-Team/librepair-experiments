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
    	Item item2 = new Item("Aged Brie",-2,9);
    	item2.updateQuality();
    	assertEquals(12, item2.quality);
  	}
    
}