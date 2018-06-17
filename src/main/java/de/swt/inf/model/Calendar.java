package de.swt.inf.model;

import java.util.LinkedList;
import java.util.List;

public class Calendar {

    private int CALENDAR_ID;

    private User user;

    private List<Termin> termin = new LinkedList<Termin>();

    public Calendar(User user) {
        this.user = user;
    }

    public void export() {
        //TO DO
    }

    public void addTermin(Termin termin) {
        this.termin.add(termin);
    }

    public List<Termin> getTermin() {
        return termin;
    }

    public int getCALENDAR_ID() {return this.CALENDAR_ID;}


}
