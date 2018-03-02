package fr.esiea.models;

public class ConjuredItem extends Item {
    
    private Item item;

    /**
     * Make any item conjured
     */
    public ConjuredItem(Item item) {
        this.item = item;
    }

    public String getName() {
        return this.item.name;
    }

    public void setName(String name) {
        this.item.name = name;
    }

    public int getQuality() {
        return this.item.quality;
    }

    public void setQuality(int quality) {
        this.item.quality = quality;
    }

    public int getSellIn() {
        return this.item.sellIn;
    }

    public void setSellIn(int sellIn) {
        this.item.sellIn = sellIn;
    }

    public int getPrice() {
        return this.item.price;
    }

    public int getQuantity() {
        return this.item.quantity;
    }

    public void setQuantity(int quantity) {
        this.item.quantity = quantity;
    }

    public String toString() {
        return this.item.name + ", sellIn : " + this.item.sellIn + ", quality : " + this.item.quality + ", price : " + this.item.price + ", quantity : " + this.item.quantity;
    }

    /**
     * The Quality of a conjured item degrades two times faster than a normal item.
     */
    public void updateQuality() {
        this.item.updateQuality();
        this.item.updateQuality();
    }
    
}
