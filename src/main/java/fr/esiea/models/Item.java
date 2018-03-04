package fr.esiea.models;

public class Item {

    protected int id;
    protected String name;
    protected int sellIn;
    protected int quality;
    protected int price;
    protected int quantity;

    public Item() {
        this.name = "";
        this.sellIn = 0;
        this.quality = 0;
    }

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public Item(String name, int sellIn, int quality, int price, int quantity) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(Item item) {
        this.name = item.name;
        this.sellIn = item.sellIn;
        this.quality = item.quality;
        this.price = item.price;
        this.quantity = item.quantity;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return this.sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String toString() {
        return this.name + ", sellIn : " + this.sellIn + ", quality : " + this.quality + ", price : " + this.price + ", quantity : " + this.quantity;
    }

    public void updateQuality() {
        this.quality -= (this.sellIn > 0) ? 1 : 2;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
