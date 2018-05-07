package de.swt.inf.model;

import de.swt.inf.database.CategoryDao;

import java.util.Collection;

public class Category {

    private int CATEGORY_ID;

    private String name;

    private short color;

    private String icon;

    //private int symbol; //brauchen wir den?

    private CategoryDao daoCategory;

    private Collection<Termin> termin;

    public Category(String name, short color) {
        this.name = name;
        this.color = color;
    }

    public Category() {
        //for prototype Impl.
    }


    //ALL SETTER
    public void setId(int id) {
        this.CATEGORY_ID = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(short color) {
        this.color = color;
    }

    public void setIcon(String icon){this.icon = icon;}


    //ALL GETTER
    public int getId() {
        return CATEGORY_ID;
    }

    public String getName() {
        return name;
    }

    public short getColor() {
        return color;
    }

    public String getIcon(){return icon;}


    public void delete() {
        //TO DO
    }

    /*public void setIcon(int symbol) {
        this.symbol = symbol;
    }*/

}
