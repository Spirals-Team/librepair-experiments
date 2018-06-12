package nl.stil4m.mollie.domain;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @see https://www.mollie.com/en/docs/reference/subscriptions/create
 * @author stevensnoeijen
 */
public class CreateSubscription {

    /**
     * The constant amount in EURO that you want to charge with each subscription payment, e.g. 100.00 if you would want to charge €100.00.
     */
    private Double amount;

    /**
     * Optional – Total number of charges for the subscription to complete. Leave empty for an ongoing subscription.
     */
    private Optional<Integer> times;

    /**
     * Interval to wait between charges like 1 month(s) or 14 days.
     * <br/>
     * Possible values: … months, … weeks, … days
     */
    private String interval;

    /**
     * Optional – The start date of the subscription in yyyy-mm-dd format. This is the first day on which your customer will be charged. When this
     * parameter is not provided, the current date will be used instead.
     */
    private Optional<String> startDate;

    /**
     * A description unique per customer. This will be included in the payment description along with the charge date in yyyy-mm-dd format.
     */
    private String description;

    /**
     * Optional – The payment method used for this subscription, either forced on creation or null if any of the customer's valid mandates may be
     * used.
     * <br/>
     * Possible values: creditcard, directdebit, null
     */
    private Optional<String> method;

    /**
     * Optional – Use this parameter to set a webhook URL for all subscription payments.
     */
    private Optional<String> webhookUrl;

    public CreateSubscription(@Nonnull Double amount, @Nullable Integer times, @Nonnull String interval, @Nullable String startDate, @Nonnull String description, @Nullable String method, @Nullable String webhookUrl) {
        this.amount = amount;
        this.times = Optional.ofNullable(times);
        this.interval = interval;
        this.startDate = Optional.ofNullable(startDate);
        this.description = description;
        this.method = Optional.ofNullable(method);
        this.webhookUrl = Optional.ofNullable(webhookUrl);
    }

    public CreateSubscription(@Nonnull Double amount, @Nonnull String interval, @Nonnull String description) {
        this(amount, null, interval, null, description, null, null);
    }

    public Double getAmount() {
        return amount;
    }

    public Optional<Integer> getTimes() {
        return times;
    }

    public String getInterval() {
        return interval;
    }

    public Optional<String> getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public Optional<String> getMethod() {
        return method;
    }

    public Optional<String> getWebhookUrl() {
        return webhookUrl;
    }
}
