package pl.kayzone.exchange.model.entity;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

import java.math.BigDecimal;
import java.util.Objects;

@Embedded
public class TransactionCurrency  {

    @Reference
    private CurrencyCourse currencyCourse;
    private BigDecimal course;
    private BigDecimal quantity;

    public CurrencyCourse getCurrencyCourse() {
        return currencyCourse;
    }

    public void setCurrencyCourse(CurrencyCourse currencyCourse) {
        this.currencyCourse = currencyCourse;
    }

    public BigDecimal getCourse() {
        return course;
    }

    public void setCourse(BigDecimal course) {
        this.course = course;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionCurrency)) return false;
        TransactionCurrency that = (TransactionCurrency) o;
        return Objects.equals(getCurrencyCourse(), that.getCurrencyCourse()) &&
                Objects.equals(getCourse(), that.getCourse()) &&
                Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCurrencyCourse(), getCourse(), getQuantity());
    }

}
