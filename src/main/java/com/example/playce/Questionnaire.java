package com.example.playce;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Questionnaire {
    private String category;
    private String distance;
    private String shouldShowAgeRestricted;
    private String ethnicity;
    private String specialty;
    private String restaurantType;
    private double latitude;
    private double longitude;
    private String activitiesOver21;
    private String price;
    private String useRating;
    private String activeActivities;
    private String inactiveActivities;
    private String inOrOutdoor;
    private String shoppingCategories;

    public String getCategory() {
       return this.category;
    }

    public String getDistance() {
       return this.distance;
    }

    public String getShouldShowAgeRestricted() {
       return this.shouldShowAgeRestricted;
    }

    public String getEthnicity() {
       return this.ethnicity;
    }

    public String getSpecialty() {
       return this.specialty;
    }

    public String getRestaurantType() {
       return this.restaurantType;
    }

    public double getLatitude() {
       return this.latitude;
    }

    public double getLongitude() {
       return this.longitude;
    }

    public String getActivitiesOver21() {
       return this.activitiesOver21;
    }

    public String getPrice() {
       return this.price;
    }

    public String getUseRating() {
       return this.useRating;
    }

    public String getActiveActivities() {
       return this.activeActivities;
    }

    public String getInactiveActivities() {
       return this.inactiveActivities;
    }

    public String getInOrOutdoor() {
       return this.inOrOutdoor;
    }

    public String getShoppingCategories() {
       return this.shoppingCategories;
    }
}
