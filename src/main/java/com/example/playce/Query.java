package com.example.playce;

public class Query {

    private final String name;
    private final boolean isDate;
    private final int minRating;
    private final boolean isGroup;
    private final boolean isOfAge;

    public Query(String name, boolean isDate, int minRating, boolean isGroup, boolean isOfAge) {
        this.name = name;
        this.isDate = isDate;
        this.minRating = minRating;
        this.isGroup = isGroup;
        this.isOfAge = isOfAge;
    }

    public String getName() {
        return name;
    }

    public boolean getIsDate() {
        return isDate;
    }

    public int getMinRating() {
        return minRating;
    }

    public boolean getIsGroup() {
        return isGroup;
    }
   
    public boolean getIsOfAge() {
        return isOfAge;
    }
}
