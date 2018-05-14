package de.swt.inf.model;

import de.swt.inf.database.UserDao;

public class User {

    private int USER_ID;

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    private boolean confirmedUser;

    private UserPreferences userPreferences;

    private WeatherReport weatherReport;

    private Calendar calendar;

    private UserDao daoUser;

    public User() {

    }

    public User(String username, String password, String email, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static User getUserByEmail(String email) {
        return new User(); //TODO:
    }

    public String getUsername() {
        return username;
    }


    //ALL SETTERS
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }


    //ALL GETTERS
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean getConfirmedUser() {
        return confirmedUser;
    }

    public void setConfirmedUser(boolean confirmedUser) {
        this.confirmedUser = confirmedUser;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public WeatherReport getWeatherReport() {
        return weatherReport;
    }

    public void setWeatherReport(WeatherReport weatherReport) {
        this.weatherReport = weatherReport;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public UserDao getDaoUser() {
        return daoUser;
    }


    //ALL METHODS

    public void setDaoUser(UserDao userDao) {
        this.daoUser = daoUser;
    }

    public Location getLocation() {
        //TO DO
        return null;
    }

    public boolean passwordReset() {
        return false;
    }

    public boolean verifyUsername() {
        return false;
    }

    public boolean verifyPassword() {
        return false;
    }

    public void confirmUser() {
        this.confirmedUser = true;
    }

}
