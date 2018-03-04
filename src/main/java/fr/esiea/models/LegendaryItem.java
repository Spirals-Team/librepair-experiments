package fr.esiea.models;

public class LegendaryItem extends Item {
    public LegendaryItem() {
    }

    public LegendaryItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public LegendaryItem(String name, int sellIn, int quality, int price, int quantity) {
        super(name, sellIn, quality, price, quantity);
    }

    /**
     * Make any item legendary
     */
    public LegendaryItem(Item item) {
        super(item);
    }
	
    public int getQuality() {
        if(this.name.toLowerCase().equals("sulfuras")){
            return 80;
        }else {
            return this.quality;
        }
    }
    
    /**
     * The Quality of a legendary item can never be altered.
     */
    public void updateQuality() {
    }
}