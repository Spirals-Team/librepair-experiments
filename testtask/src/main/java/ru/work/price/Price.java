package ru.work.price;

import java.util.Date;
import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Price {
    private long id;
    private String product_code;
    private int number;
    private int depart;
    private Date begin;
    private Date end;
    private long value;

    public Price() {
    }

    public Price(String product_code, int number, int depart, Date begin, Date end, long value) {
        this.product_code = product_code;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public Price(long id, String product_code, int number, int depart, Date begin, Date end, long value) {
        this.id = id;
        this.product_code = product_code;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        return id == price.id
                && number == price.number
                && depart == price.depart
                && value == price.value
                && Objects.equals(product_code, price.product_code)
                && Objects.equals(begin, price.begin)
                && Objects.equals(end, price.end);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, product_code, number, depart, begin, end, value);
    }

    @Override
    public String toString() {
        return new StringBuilder("id=").append(id)
                .append(", product_code=").append(product_code)
                .append(", number=").append(number)
                .append(", depart=").append(depart)
                .append(", begin=").append(begin)
                .append(", end=").append(end)
                .append(", value=").append(value)
                .append(";").toString();
    }
}
