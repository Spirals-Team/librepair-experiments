package de.swt.inf.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {

    public static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[,.!%$#_&@])(?=\\S+$).{6,}$";

    public static final String userNameRegex = "(\\w)*(\\d)*(\\w)*.{6,}"; //TODO: Richtign Regex für Username

    private int USER_ID;

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    private boolean confirmedUser = false;

    private UserPreferences userPreferences;

    private WeatherReport weatherReport;

    private Calendar calendar;

    //private UserDao daoUser; // wozu war das vorgesehen?

    public User() {

    }

    public User(String username, String password, String email, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.calendar = new Calendar(this);
    }

    public static User getUserByEmail(String email) {
        return new User(); //TODO:
    }

    public String getUsername() {
        return username;
    }


    //ALL SETTERS
    public void setUSER_ID(int id) {this.USER_ID = id;}

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

    /*public UserDao getDaoUser() {
        return daoUser;
    }*/


    //ALL METHODS

    /*public void setDaoUser(UserDao userDao) {
        this.daoUser = daoUser;
    }*/

    public Location getLocation() {
        //TO DO
        return null;
    }

    public boolean passwordReset() {
        return false;
    }

    public boolean verifyUsername() throws IOException {
        return username.matches(userNameRegex) && checkBlacklistedUsernames(username);
    }

    public boolean verifyPassword() {
        return password.matches(passwordRegex);
    }

    public void confirmUser() {
        this.confirmedUser = true;
    }

    public static boolean checkBlacklistedUsernames(String userName) throws IOException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/usernameBlacklist.txt"));
            String sCurrentLine;
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                if (userName.toUpperCase().contains(sCurrentLine.toUpperCase())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Cannot Check Blacklisted Usernames.\nContact an Systemadmin.");
        }
        return false;
    }

}
