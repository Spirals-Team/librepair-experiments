package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class AntifraudAnalysis extends PagarMeModel<Integer> {

    @Expose(serialize = false)
    private Integer cost;

    @Expose(serialize = false)
    private String name;

    @Expose(serialize = false)
    @SerializedName("date_updated")
    private DateTime updatedAt;

    @Expose(serialize = false)
    private BigDecimal score;

    @Expose(serialize = false)
    private Status status;

    public Integer getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public BigDecimal getScore() {
        return score;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void setId(Integer id) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    public enum Status {

        @SerializedName("approved")
        APPROVED,

        @SerializedName("failed")
        FAILED,

        @SerializedName("processing")
        PROCESSING,

        @SerializedName("refused")
        REFUSED

    }

}
