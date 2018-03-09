package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class PostbackDelivery extends PagarMeModel<String> {

    @Expose(serialize = false)
    @SerializedName("response_time")
    private Integer responseTime;

    @Expose(serialize = false)
    @SerializedName("response_body")
    private String responseBody;

    @Expose(serialize = false)
    @SerializedName("response_headers")
    private String responseHeaders;

    @Expose(serialize = false)
    @SerializedName("status_code")
    private String statusCode;

    @Expose(serialize = false)
    @SerializedName("status_reason")
    private String statusReason;

    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    @Expose(serialize = false)
    private Transaction.Status status;

    public Integer getResponseTime() {
        return responseTime;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public Transaction.Status getStatus() {
        return status;
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
