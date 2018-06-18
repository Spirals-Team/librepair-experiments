package de.swt.inf.database;

import de.swt.inf.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserPreferencesDaoImpl implements UserPreferencesDao{

    private Connection connection;

    public UserPreferencesDaoImpl(Connection connection){this.connection =connection;}

    @Override
    public UserPreferences getUserPreferences(int id) {
        try{
            String query = "SELECT * FROM userpreferences WHERE USER_PREFERENCES_ID = " + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            UserPreferences tempUserPref = new UserPreferences();
            tempUserPref.setProvince(ProvinceEnum.valueOf(rs.getString("Province")));
            tempUserPref.setUniversity(UniversityEnum.valueOf(rs.getString("University")));
            tempUserPref.setCourse(CourseEnum.valueOf(rs.getString("Course")));
            tempUserPref.setSemester(rs.getString("Semester"));
            tempUserPref.setMusic(MusicPreferencesEnum.valueOf(rs.getString("Music")));
            tempUserPref.setGender(GenderEnum.valueOf(rs.getString("Gender")));
            tempUserPref.setAge(rs.getString("Age"));

            return tempUserPref;

        }catch (SQLException e){
            System.err.println(e);
        }
        return null;
    }

    @Override
    public boolean updateUserPreferences(UserPreferences userPreferences) {
      try{
            String query = "UPDATE userpreferences SET Province = '" + userPreferences.getProvince() + "', University ='" + userPreferences.getUniversityEnum() +
                "', Course = '" + userPreferences.getCourseEnum() + "' , Semester = '" + userPreferences.getSemester() + "', Music = '"
                    + userPreferences.getMusicPreferencesEnum() + "' , Gender = '" + userPreferences.getGenderEnum() + "', Age = '" + userPreferences.getAge() +
                    "' WHERE USER_PREFERENCES_ID = '" + userPreferences.getId() + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }


    public boolean deleteUserPreferences(int id) {
        try{
            String query = "DELETE FROM userpreferences WHERE USER_PREFERENCES_ID = " + id;
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }

    @Override
    public boolean addUserPreferences(UserPreferences userPreferences) {
        try {
            String query = "INSERT INTO userpreferences VALUES ('" + userPreferences.getProvince() + "', '" + userPreferences.getUniversityEnum() + "', '" +
                    userPreferences.getCourseEnum() + "', '" + userPreferences.getSemester() + "', '" + userPreferences.getMusicPreferencesEnum() + "', '" +
                    userPreferences.getGenderEnum() + "', '" + userPreferences.getAge() + "')";
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        }catch(SQLException e){
            System.err.println(e);
        }
            return false;
        }
}
