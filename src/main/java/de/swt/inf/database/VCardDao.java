package de.swt.inf.database;

import de.swt.inf.model.VCard;

import java.util.List;

public interface VCardDao {

    public abstract VCard getVCard(VCard vCard);

    public abstract VCard getVCard(int id);

    public abstract List<VCard> getAllVCards();

    public abstract boolean updateVCard(VCard vCard);

    public abstract boolean deleteVCard(VCard vCard);

    public abstract boolean deleteVCard(int id);

    public abstract boolean addVCard(VCard vCard);

}
