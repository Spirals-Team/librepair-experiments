package de.swt.inf.model;

import de.swt.inf.database.VCardDao;

public class VCard {

    private int VCARD_ID;

    private String firstname;

    private String lastname;

    private String telNr;

    private String office;

    private String title;

    private String email;

    private String note;

    private VCardDao daoVCard;


    public VCard(String firstname, String lastname, String telNr, String office, String title, String email, String note) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.telNr = telNr;
        this.office = office;
        this.title = title;
        this.email = email;
        this.note = note;
    }

    public VCard() {
        //for Prototype impl.
    }


    //ALL SETTER
    public void setId(int id){this.VCARD_ID = id;}

    public void setFirstname(String firstname){this.firstname = firstname;}

    public void setLastname(String lastname){this.lastname = lastname;}

    public void setTelNr(String telNr){this.telNr = telNr;}

    public void setOffice(String office){this.office = office;}

    public void setTitle(String title){this.title = title;}

    public void  setEmail(String email){this.email = email;}

    public void  setNote(String note){this.note = note;}


    //ALL GETTER
    public VCard getVCard() {
        return this;
    }

    public int getId() {return VCARD_ID;}

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getTelNr() {
        return this.telNr;
    }

    public String getOffice() {
        return this.office;
    }

    public String getTitle() {
        return this.title;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNote() {
        return this.note;
    }

}
