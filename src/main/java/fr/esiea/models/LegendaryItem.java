package fr.esiea.models;

public class LegendaryItem extends Item {

    private Item item;

    /**
     * Make any item legendary
     */
    public LegendaryItem(Item item) {
        this.item = item;
    }

    public String getName() {
        return this.item.name;
    }

    public void setName(String name) {
        this.item.name = name;
    }

    public int getQuality() {
        if(this.item.name.toLowerCase().equals("sulfuras")){
            return 80;
        }else {
            return this.item.quality;
        }
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

    public int getSellIn() {
        return this.item.sellIn;
    }

    public String toString() {
        return this.item.name + ", sellIn : " + this.item.sellIn + ", quality : " + this.item.quality + ", price : " + this.item.price + ", quantity : " + this.item.quantity;
    }

    /**
     * The Quality of a legendary item can never be altered.
     */
    public void updateQuality() {
    }

}
