package me.pagarme.factory;

import org.joda.time.LocalDate;

import org.joda.time.format.DateTimeFormat;

import org.joda.time.format.DateTimeFormatter;

import me.pagar.model.Card;

public class CardFactory {

    public static String CARD_HOLDER_NAME = "Pagarme LTDA";
    public static String CARD_NUMBER = "4111111111111111";
    public static LocalDate date = LocalDate.now().plusYears(1);
    public static Integer CARD_CVV = 401;

    public Card create(){
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MMyy");
        String expiresAt = date.toString(dtf);

        Card card = new Card();
        card.setHolderName(CARD_HOLDER_NAME);
        card.setNumber(CARD_NUMBER);
        card.setExpiresAt(expiresAt);
        card.setCvv(CARD_CVV);
        return card;
    }
}