package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Chargeback {

    private final String id;

    private final String payment;

    private final Double amount;

    private final Date chargebackDatetime;

    private final Date reversedDatetime;

    @JsonCreator
    public Chargeback(@JsonProperty("id") String id,
            @JsonProperty("payment") String payment,
            @JsonProperty("amount") Double amount,
            @JsonProperty("status") String status,
            @JsonProperty("chargebackDatetime") Date chargebackDatetime,
            @JsonProperty("reversedDatetime") Date reversedDatetime) {
        this.id = id;
        this.payment = payment;
        this.amount = amount;
        this.chargebackDatetime = chargebackDatetime;
        this.reversedDatetime = reversedDatetime;
    }

    public String getId() {
        return id;
    }

    public String getPayment() {
        return payment;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getChargebackDatetime() {
        return chargebackDatetime;
    }

    public Date getReversedDatetime() {
        return reversedDatetime;
    }

}
