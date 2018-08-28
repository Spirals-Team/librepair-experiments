package ru.job4j.sqlru.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 20.04.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class LoadSetting {
    private static final Logger LOG = LoggerFactory.getLogger(LoadSetting.class);
    public final String configFile = "ConfigSQL_RU.properties";
    public String url;
    public String user;
    public String password;
    public int year;
    public int month;
    public int date;

    public void load() {
        final PropertiesParser ServerSettings = new PropertiesParser(configFile);
        url = ServerSettings.getString("Url", "jdbc:postgresql://localhost:5432/sql_ru");
        user = ServerSettings.getString("User", "postgres");
        password = ServerSettings.getString("Password", "postgres");
        year = Integer.parseInt(ServerSettings.getString("Year", "2007"));
        month = Integer.parseInt(ServerSettings.getString("Month", "1"));
        date = Integer.parseInt(ServerSettings.getString("Date", "1"));
    }


}
