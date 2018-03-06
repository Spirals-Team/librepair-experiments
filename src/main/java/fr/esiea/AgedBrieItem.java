package fr.esiea;

import java.lang.String;

public class AgedBrieItem extends Item {

	public AgedBrieItem(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
	}

	public void updateQuality() {
		if(this.quality < MAX_QUALITY)
			quality ++;
		if(this.sellIn < SELLIN_IS_OVER)
			quality ++;
	}

}