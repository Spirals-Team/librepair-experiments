package pl.kayzone.exchange.model.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.mongodb.morphia.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(value = "courses")
@Indexes({
        @Index(value = "idCode", fields = @Field("idCode")),
        @Index(value = "active", fields = @Field("active"))
})
public class CurrencyCourse extends BaseEntity implements Serializable
{
    @Reference
    private Currency idCode;
    private LocalDateTime date;
    private LocalDateTime validTo;
    private BigDecimal bid;
    private BigDecimal ask;
    private Boolean active;
    private final static long serialVersionUID = 6513736575303961048L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CurrencyCourse() {
    }

    /**
     *
     * @param validTo
     * @param idCode
     * @param active
     * @param ask
     * @param date
     * @param bid
     */
    public CurrencyCourse(Currency idCode, LocalDateTime date, LocalDateTime validTo, BigDecimal bid, BigDecimal ask, Boolean active) {
        super();
        this.idCode = idCode;
        this.date = date;
        this.validTo = validTo;
        this.bid = bid;
        this.ask = ask;
        this.active = active;
    }

    public Currency getIdCode() {
        return idCode;
    }

    public void setIdCode(Currency idCode) {
        this.idCode = idCode;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }


    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStrictNBPuriAddress() {
        String result = getIdCode().getUrlNbp()
                .replace("{table}",getIdCode().getTablesType())
                .replace("{code}",getIdCode().getIdCode());
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idCode", idCode)
                .append("date", date)
                .append("validTo", validTo)
                .append("bid", bid)
                .append("ask", ask)
                .append("active", active).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyCourse)) return false;
        CurrencyCourse that = (CurrencyCourse) o;
        return  Objects.equals(getIdCode(), that.getIdCode()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getValidTo(), that.getValidTo()) &&
                Objects.equals(getBid(), that.getBid()) &&
                Objects.equals(getAsk(), that.getAsk()) &&
                Objects.equals(getActive(), that.getActive());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdCode(), getDate(), getValidTo(), getBid(), getAsk(), getActive());
    }
    public boolean isLikeNull() {
        if (  getAsk().compareTo(BigDecimal.ZERO) == 0 &&
              getBid().compareTo(BigDecimal.ZERO) == 0 &&
                getDate()==null && getValidTo() ==null  && getActive() ==null  ) {
            return true;
        }
        else return false;
    }

}

