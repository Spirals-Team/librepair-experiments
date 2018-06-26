/*
 * Currency.java
 *
 * Created on 08 February 2005, 16:12
 */

package coaching.money;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Big Decimal Money class.
 */
public final class BigDecimalMoney {

    /** The currency. */
    private Currency currency = Currency.getInstance(Locale.getDefault());

    /** The amount. */
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * Instantiates a new money instance.
     */
    public BigDecimalMoney() {
        super();
    }

    /**
     * Instantiates a new money instance.
     *
     * @param amount
     *            the amount
     */
    public BigDecimalMoney(final long amount) {
        this(new BigDecimal(amount));
    }

    /**
     * Instantiates a new money instance.
     *
     * @param bigDecimal
     *            the big decimal
     */
    public BigDecimalMoney(final BigDecimal bigDecimal) {
        this(bigDecimal, Locale.getDefault());
    }

    /**
     * Instantiates a new money instance.
     *
     * @param amount
     *            the amount
     */
    public BigDecimalMoney(final String amount) {
        setCurrency(Currency.getInstance(Locale.getDefault()));
        setAmount(new BigDecimal(amount));
    }

    /**
     * Instantiates a new money instance.
     *
     * @param amount
     *            the amount
     * @param locale
     *            the locale
     */
    public BigDecimalMoney(final BigDecimal amount, final Locale locale) {
        this(amount, Currency.getInstance(locale));
    }

    /**
     * Instantiates a new money instance.
     *
     * @param amount
     *            the amount
     * @param currency
     *            the currency
     */
    public BigDecimalMoney(final BigDecimal amount, final Currency currency) {
        super();
        setCurrency(currency);
        setAmount(amount);
    }

    /**
     * Instantiates a new money instance.
     *
     * @param currency
     *            the currency
     */
    public BigDecimalMoney(final Currency currency) {
        this(currency, BigDecimal.ZERO);
    }

    /**
     * Instantiates a new money instance.
     *
     * @param currency
     *            the currency
     * @param amount
     *            the amount
     */
    public BigDecimalMoney(final Currency currency, final BigDecimal amount) {
        super();
        setCurrency(currency);
        setAmount(amount);
    }

    /**
     * set the currency.
     *
     * @param currency
     *            the currency
     * @return the big decimal money
     */
    public BigDecimalMoney setCurrency(final Currency currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Sets the amount.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney setAmount(final long amount) {
        this.amount = new BigDecimal(amount);
        return this;
    }

    /**
     * Sets the amount.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney setAmount(final MoneyInterface amount) {
        this.amount = new BigDecimal(amount.getAmount());
        return this;
    }

    /**
     * Sets the amount.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney setAmount(final BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Adds a long value.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney add(final long amount) {
        this.amount = this.amount.add(new BigDecimal(amount));
        return this;
    }

    /**
     * Adds a BigDecimal amount.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney add(final BigDecimalMoney amount) {
        this.amount = this.amount.add(amount.asBigDecimal());
        return this;
    }

    /**
     * Subtract.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney subtract(final long amount) {
        this.amount = this.amount.subtract(new BigDecimal(amount));
        return this;
    }

    /**
     * Subtract an BigDecimal amount.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney subtract(final BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
        return this;
    }

    /**
     * Subtract.
     *
     * @param amount
     *            the amount
     * @return the big decimal money
     */
    public BigDecimalMoney subtract(final BigDecimalMoney amount) {
        this.amount = this.amount.subtract(amount.asBigDecimal());
        return this;
    }

    /**
     * currency.
     *
     * @return the currency
     */
    public Currency getCurrency() {
        return this.currency;
    }

    /**
     * Gets the currency code.
     *
     * @return the currency code
     */
    public String getCurrencyCode() {
        return this.currency.getCurrencyCode();
    }

    /**
     * Gets the symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return this.currency.getSymbol();
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return this.currency.getDisplayName();
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Long getAmount() {
        return this.amount.longValue();
    }

    /**
     * As long.
     *
     * @return the long
     */
    public long asLong() {
        return this.amount.longValue();
    }

    /**
     * As big decimal.
     *
     * @return the big decimal
     */
    private BigDecimal asBigDecimal() {
        return this.amount;
    }

    /**
     * Checks if is more than.
     *
     * @param amount
     *            the amount
     * @return true, if successful, otherwise false., otherwise false. more than
     */
    public boolean isMoreThan(final long amount) {
        return this.amount.longValue() > amount;
    }

    /**
     * Checks if is equal to.
     *
     * @param amount
     *            the amount
     * @return true, if successful, otherwise false., otherwise false. equal to
     */
    public boolean isEqualTo(final long amount) {
        return this.amount.longValue() == amount;
    }

    /**
     * Checks if is less than.
     *
     * @param amount
     *            the amount
     * @return true, if successful, otherwise false., otherwise false. less than
     */
    public boolean isLessThan(final long amount) {
        return this.amount.longValue() < amount;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        final String currencyString = numberFormat.format(this.amount);
        return String.format("%s", currencyString);
    }

}
