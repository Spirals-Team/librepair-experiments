package ru.job4j.inheritance;

public class Engineer extends Profession {
    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String tellAboutMyself() {
        return "I am " + field + " engineer";
    }
}
