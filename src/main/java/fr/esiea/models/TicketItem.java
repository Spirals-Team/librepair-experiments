package fr.esiea.models;

public class TicketItem extends Item {

    public TicketItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }



    public TicketItem(Item item) {
        super(item);
    }

    /**
     * Ticket increases in Quality as its SellIn value approaches;
     * Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * Quality drops to 0 after the concert
     */
    public void updateQuality() {
        this.quality +=(this.sellIn < 0) ? (-this.quality): (this.sellIn <= 5) ? 3 : (this.sellIn <= 10) ? 2 : 1;
    }

}
