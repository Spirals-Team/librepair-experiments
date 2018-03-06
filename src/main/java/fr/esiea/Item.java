package fr.esiea;

import java.lang.String;

public class Item {

    public String name;
    public int sellIn;
    public int quality;
    public static final int SELLIN_IS_OVER = 0;
    public static final int MAX_QUALITY = 50;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public int getQuality() {
        return this.quality;
    }

    public void updateQuality() {

    }

    public void updateSellIn() {
        this.sellIn --;
    }

    public void updateItem() {
        updateQuality();
        updateSellIn();
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }


}