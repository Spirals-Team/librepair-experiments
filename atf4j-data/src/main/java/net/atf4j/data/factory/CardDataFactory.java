/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.data.factory;

import net.atf4j.data.Card;

/**
 * Factory class for making charge/credit card test data.
 */
public final class CardDataFactory extends AbstractDataFactory {

    /** Default filename for card data. */
    private static final String CARD_DATA_TXT = "cardData.txt";

    /** card data factory instance. */
    private static CardDataFactory cardDataFactory = null;

    /**
     * Gets the single INSTANCE of CardDataFactory.
     *
     * @return single INSTANCE of CardDataFactory
     */
    public static CardDataFactory getInstance() {
        if (CardDataFactory.cardDataFactory == null) {
            CardDataFactory.cardDataFactory = new CardDataFactory();
        }
        return CardDataFactory.cardDataFactory;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private CardDataFactory() {
        super();
        initialise();
    }

    /**
     * Initialise.
     */
    protected void initialise() {
        this.lines = load(CARD_DATA_TXT);
    }

    /**
     * Create new INSTANCE of create.
     *
     * @return the card
     */
    public static Card create() {
        return new Card();
    }

    /**
     * Random card.
     *
     * @return the string
     */
    public static Card random() {
        return new Card();
    }

}
