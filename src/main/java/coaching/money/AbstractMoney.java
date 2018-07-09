
package coaching.money;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An Abstract Money class.
 */
public abstract class AbstractMoney implements MoneyInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The currency. */
    private Currency currency = Currency.getInstance(Locale.getDefault());

    /** The amount. */
    private Long amount = 0L;

    /**
     * Instantiates a new abstract money.
     */
    public AbstractMoney() {
        super();
    }

    /**
     * Instantiates a new abstract money.
     *
     * @param currency
     *            the currency
     */
    public AbstractMoney(final Currency currency) {
        super();
        setCurrency(currency);
    }

    /**
     * Instantiates a new abstract money.
     *
     * @param amount
     *            the amount
     */
    public AbstractMoney(final long amount) {
        setCurrency(Currency.getInstance(Locale.getDefault()));
        setAmount(amount);
    }

    /**
     * Instantiates a new abstract money.
     *
     * @param currency
     *            the currency
     * @param amount
     *            the amount
     */
    public AbstractMoney(final Currency currency, final Long amount) {
        super();
        setCurrency(currency);
        setAmount(amount);
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#setCurrency(java.util.Currency)
     */
    @Override
    public MoneyInterface setCurrency(final Currency currency) {
        this.currency = currency;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#setAmount(long)
     */
    @Override
    public MoneyInterface setAmount(final long amount) {
        this.amount = amount;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#setAmount(coaching.money.Money)
     */
    @Override
    public MoneyInterface setAmount(final MoneyInterface amount) {
        this.amount = amount.getAmount();
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#getCurrency()
     */
    @Override
    public Currency getCurrency() {
        return this.currency;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#getCurrencyCode()
     */
    @Override
    public String getCurrencyCode() {
        return this.currency.getCurrencyCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#getAmount()
     */
    @Override
    public Long getAmount() {
        return this.amount;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#getSymbol()
     */
    @Override
    public String getSymbol() {
        return this.currency.getSymbol();
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#getDisplayName()
     */
    @Override
    public String getDisplayName() {
        return this.currency.getDisplayName();
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#add(long)
     */
    @Override
    public MoneyInterface add(final long amount) {
        this.amount += amount;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#add(coaching.money.Money)
     */
    @Override
    public MoneyInterface add(final MoneyInterface amount) {
        this.amount += amount.getAmount();
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#subtract(long)
     */
    @Override
    public MoneyInterface subtract(final long amount) {
        this.amount -= amount;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#subtract(coaching.money.Money)
     */
    @Override
    public MoneyInterface subtract(final MoneyInterface amount) {
        this.amount -= amount.getAmount();
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#isMoreThan(long)
     */
    @Override
    public boolean isMoreThan(final long amount) {
        return this.amount > amount;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#isMoreThan(coaching.money.Money)
     */
    @Override
    public boolean isMoreThan(final MoneyInterface money) {
        return this.amount > money.getAmount();
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#isEqualTo(long)
     */
    @Override
    public boolean isEqualTo(final long amount) {
        return this.amount == amount;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#isEqualTo(coaching.money.Money)
     */
    @Override
    public boolean isEqualTo(final MoneyInterface money) {
        return this.amount == money.getAmount();
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#isLessThan(long)
     */
    @Override
    public boolean isLessThan(final long amount) {
        return this.amount < amount;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.money.MoneyInterface#isLessThan(coaching.money.Money)
     */
    @Override
    public boolean isLessThan(final MoneyInterface money) {
        return this.amount < money.getAmount();
    }

    /**
     * To log.
     */
    public void toLog() {
        final String debug = String.format("%s [currency=%s, amount=%s]", this.getClass().getSimpleName(), this.currency, this.amount);
        this.log.info(debug);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final String symbol = this.currency.getSymbol();
        final String pattern = String.format("%s ###,###.##", symbol);
        final DecimalFormat formatter = new DecimalFormat(pattern);
        return formatter.format(this.amount);
    }

}
