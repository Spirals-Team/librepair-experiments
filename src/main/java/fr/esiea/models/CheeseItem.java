package fr.esiea.models;

public class CheeseItem extends Item {

    public CheeseItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public CheeseItem(Item item) {
        super(item);
    }

    /**
     * Cheese actually increases in Quality the older it gets
     */
    public void updateQuality() {
        this.quality += (this.sellIn > 0) ? 1 : 2;
    }

};
