package ch.ntb.jass.common.entities;


public class CardEntity {
	//Fields
	//TODO Maybe use private final fields and constructor?
	public CardColorEntity color;
    public CardValueEntity value;

    //Methods

    /** Composes the color and value of this card into an ID.
     * @return The ID representative of this card.
     */
    public int calcId() {
    	//Compose ID
		return color.getId()*10 + value.getId();
	}

    //TODO Maybe use a Constructor instead?
    /** Creates a new CardEntity out of its ID representative.
     * @param cardId The ID which represents the created card.
     * @return A new CardEntity.
     */
    public static CardEntity getById(int cardId) {
    	CardEntity card = new CardEntity();
    	card.color = CardColorEntity.getById(getColorId(cardId));
    	card.value = CardValueEntity.getById(getValueId(cardId));
    	return card;
    }

    /**
     * @param Decomposes a card ID into the ID of the color.
     * @return The ID representative of the color.
     */
    private static int getColorId(int cardId) {return cardId/10;}

    /**
     * @param Decomposes a card ID into the ID of the value.
     * @return The ID representative of the value of this card.
     */
    private static int getValueId(int cardId) {return cardId%10;}

}