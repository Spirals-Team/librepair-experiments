package pl.arek.domain;

public class Undead {
    private int id;
    private String type;

    public Undead(int id, String type) {
    }
    public Undead(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
