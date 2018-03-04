package fr.esiea.models;

public class ConjuredItem extends Item {
    
    public ConjuredItem() {
    }

    public ConjuredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public ConjuredItem(String name, int sellIn, int quality, int price, int quantity) {
        super(name, sellIn, quality, price, quantity);
    }

    /**
     * Make any item conjured
     */
    public ConjuredItem(Item item) {
        super(item);
    }
    
    /**
     * The Quality of a conjured item degrades two times faster than a normal item.
     */
    public void updateQuality() {
    	super.updateQuality();
    	super.updateQuality();
    }
}
