package de.swt.inf.database;

import de.swt.inf.model.UserPreferences;

public interface UserPreferencesDao {

    public UserPreferences getUserPreferences(int id);

    public boolean updateUserPreferences(UserPreferences userPreferences);

    public boolean deleteUserPreferences(int id);

    public boolean addUserPreferences(UserPreferences userPreferences);
}
