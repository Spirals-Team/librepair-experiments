package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * @see https://www.mollie.com/en/docs/reference/subscriptions/get
 * @author stevensnoeijen
 */
public class Subscription {

    /**
     * Indicates the response contains a subscription object.
     * <br/>
     * Possible values: subscription
     */
    private String resource;

    /**
     * The subscription's unique identifier, for example sub_rVKGtNd6s3.
     */
    private String id;

    /**
     * The customer's unique identifier, for example cst_stTC2WHAuS.
     */
    private String customerId;

    /**
     * The mode used to create this subscription.Mode determines whether the payments are real or test payments.
     * <br/>
     * Possible values: live, test
     */
    private String mode;

    /**
     * The subscription's date and time of creation, in ISO 8601 format.
     */
    private Date createdDatetime;

    /**
     * The subscription's current status, depends on whether the customer has a pending, valid or invalid mandate.
     * <br/>
     * Possible values: pending, active, cancelled, suspended, completed
     */
    private String status;

    /**
     * The constant amount that is charged with each subscription payment
     */
    private Double amount;

    /**
     * Total number of charges for the subscription to complete
     */
    private Integer times;

    /**
     * Interval to wait between charges like 1 month(s) or 14 days.
     * <br/>
     * Possible values: … months, … weeks, … days.
     */
    private String interval;

    /**
     * The start date of the subscription in yyyy-mm-dd format.
     */
    private Date startDate;

    /**
     * A description unique per customer. This will be included in the payment description along with the charge date in yyyy-mm-dd format.
     */
    private String description;

    /**
     * The payment method used for this subscription, either forced on creation by specifying the method parameter, or null if any of the customer's valid mandates may be used.
     * <br/>
     * Possible values: creditcard, directdebit, null
     */
    private String method;

    /**
     * The subscription's date of cancellation, in ISO 8601 format.
     */
    private Date cancelledDatetime;

    /**
     * An object with URLs important to the payment process.
     * Only webhookUrl can be set.
     */
    private Links links;

    public Subscription(@JsonProperty("resource") String resource,
            @JsonProperty("id") String id,
            @JsonProperty("customerId") String customerId,
            @JsonProperty("mode") String mode,
            @JsonProperty("createdDatetime") Date createdDatetime,
            @JsonProperty("status") String status,
            @JsonProperty("amount") Double amount,
            @JsonProperty("times") Integer times,
            @JsonProperty("interval") String interval,
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("description") String description,
            @JsonProperty("method") String method,
            @JsonProperty("cancelledDatetime") Date cancelledDatetime,
            @JsonProperty("links") Links links) {
        this.resource = resource;
        this.id = id;
        this.customerId = customerId;
        this.mode = mode;
        this.createdDatetime = createdDatetime;
        this.status = status;
        this.amount = amount;
        this.times = times;
        this.interval = interval;
        this.startDate = startDate;
        this.description = description;
        this.method = method;
        this.cancelledDatetime = cancelledDatetime;
        this.links = links;
    }

    public String getResource() {
        return resource;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMode() {
        return mode;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public String getStatus() {
        return status;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getTimes() {
        return times;
    }

    public String getInterval() {
        return interval;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public String getMethod() {
        return method;
    }

    public Date getCancelledDatetime() {
        return cancelledDatetime;
    }

    public Links getLinks() {
        return links;
    }

}
