package me.pagar.model;

import com.google.gson.annotations.Expose;
import org.joda.time.DateTime;

public class Phone extends PagarMeModel<Integer> {

    @Expose
    private String ddi;

    @Expose
    private String ddd;

    @Expose
    private String number;

    public Phone() {
        super();
    }

    public Phone(final String ddd, final String number) {
        this();
        this.ddd = ddd;
        this.number = number;
    }

    public String getDdi() {
        return ddi;
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public DateTime getCreatedAt() {
        throw new UnsupportedOperationException("Not allowed.");
    }

    public void setDdi(final String ddi) {
        this.ddi = ddi;
        addUnsavedProperty("ddi");
    }

    public void setDdd(final String ddd) {
        this.ddd = ddd;
        addUnsavedProperty("ddd");
    }

    public void setNumber(final String number) {
        this.number = number;
        addUnsavedProperty("number");
    }

    @Override
    public void setId(Integer id) {
        throw new UnsupportedOperationException("Not allowed.");
    }

    @Override
    public void setClassName(String className) {
        throw new UnsupportedOperationException("Not allowed.");
    }


}
