package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.RadesAddBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.Set;


@RadesAddBuilder
public class Person {

    public enum Farbe {
        ROT,
        GELB,
        GRÜN,
        BLAU,
        LILA,
        SCHWARZ,
        BRAUN,
        WEISS
    }

    private int id;

    @NotNull
    protected String name;

    // check for package scope
    String vorname;

    protected LocalDate birthday;

    public int groesse;

    protected Set<Farbe> lieblingsfarben;

    protected Map<String, Person> freunde;


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int computeAge() {
        if (this.birthday == null) {
            throw new IllegalStateException("birthday is unknow because is jet not set");
        }
        final Period period = birthday.until(LocalDate.now());
        return period.getYears();
    }

}
