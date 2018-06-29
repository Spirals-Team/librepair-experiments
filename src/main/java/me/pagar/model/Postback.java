package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Collection;

public class Postback extends PagarMeModel<String> {

    @Expose(serialize = false)
    private Integer retries;

    @Expose(serialize = false)
    private String headers;

    @Expose(serialize = false)
    private String model;

    @Expose(serialize = false)
    @SerializedName("model_id")
    private String modelId;

    @Expose(serialize = false)
    @SerializedName("next_retry")
    private String nextRetry; // ???

    @Expose(serialize = false)
    private String payload;

    @Expose(serialize = false)
    @SerializedName("request_url")
    private String requestUrl;

    @Expose(serialize = false)
    private String signature;

    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    @Expose(serialize = false)
    private Collection<PostbackDelivery> deliveries;

    @Expose(serialize = false)
    private Transaction.Status status;

    public Integer getRetries() {
        return retries;
    }

    public String getHeaders() {
        return headers;
    }

    public String getModel() {
        return model;
    }

    public String getModelId() {
        return modelId;
    }

    public String getNextRetry() {
        return nextRetry;
    }

    public String getPayload() {
        return payload;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getSignature() {
        return signature;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public Transaction.Status getStatus() {
        return status;
    }

    public Collection<PostbackDelivery> getDeliveries() {
        return deliveries;
    }

    @Override
    public void setId(String id) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }

}
