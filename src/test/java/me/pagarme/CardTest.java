package me.pagarme;

import me.pagar.model.Card;
import me.pagar.model.PagarMeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CardTest extends BaseTest {

    @Before
    public void setUp() {
        super.setUp();
        card = new Card();
    }

    Card card;

    private static String HOLDER_NAME = "Pagarme LTDA";
    private static String CARD_NUMBER = "4111111111111111";
    private static String CARD_EXPIRATION_DATE = "092019";
    private static Integer CARD_CVV = 401;
    private static String EXPIRATION_DATE = "1022";

    @Test
    public void testDateExistence() throws PagarMeException{
        card.setHolderName(HOLDER_NAME);
        card.setNumber(CARD_NUMBER);
        card.setExpiresAt(CARD_EXPIRATION_DATE);
        card.setCvv(CARD_CVV);
        card.setExpiresAt(EXPIRATION_DATE);
        card.save();
        
        Assert.assertNotNull(card.getCreatedAt());
        Assert.assertNotNull(card.getUpdatedAt());
    }
    
    @Test
    public void testCreateCard() {

        card.setHolderName(HOLDER_NAME);
        card.setNumber(CARD_NUMBER);
        card.setExpiresAt(CARD_EXPIRATION_DATE);
        card.setCvv(CARD_CVV);
        card.setExpiresAt(EXPIRATION_DATE);

        try {

            card.save();
            Assert.assertNotNull(card.getId());

        } catch (PagarMeException exception) {
            throw new UnsupportedOperationException(exception);
        }
    }

    @Test
    public void testCreateCardFind() {

        card.setHolderName(HOLDER_NAME);
        card.setNumber(CARD_NUMBER);
        card.setExpiresAt(CARD_EXPIRATION_DATE);
        card.setCvv(CARD_CVV);
        card.setExpiresAt(EXPIRATION_DATE);

        try {

            card.save();
            Card cardFind = card.find(card.getId());

            Assert.assertNotNull(cardFind.getId());
            Assert.assertEquals(cardFind.getHolderName(), HOLDER_NAME);

        } catch (PagarMeException exception) {
            throw new UnsupportedOperationException(exception);
        }
    }
}
