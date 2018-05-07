package de.swt.inf.database;

import de.swt.inf.model.Termin;

import java.util.List;

public interface TerminDao {

    public abstract Termin getTermin(Termin termin);

    public abstract boolean updateTermin(Termin termin);

    public abstract boolean deleteTermin(Termin termin);

    public abstract boolean deleteTermin(int id);

    public abstract boolean addTermin(Termin termin);

    public abstract List<Termin> getAllTermine();

    public abstract Termin getTermin(int id);
}
