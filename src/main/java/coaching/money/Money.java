
package coaching.money;

import java.util.Currency;

/**
 * Money class.
 */
public class Money extends AbstractMoney {

    /**
     * Instantiates a new money.
     */
    public Money() {
        super();
    }

    /**
     * Instantiates a new money.
     *
     * @param currency
     *            the currency
     */
    public Money(final Currency currency) {
        super(currency);
    }

    /**
     * Instantiates a new money.
     *
     * @param amount
     *            the amount
     */
    public Money(final long amount) {
        super(amount);
    }

    /**
     * Instantiates a new money.
     *
     * @param currency
     *            the currency
     * @param amount
     *            the amount
     */
    public Money(final Currency currency, final long amount) {
        super();
        setCurrency(currency);
        setAmount(amount);
    }

}
