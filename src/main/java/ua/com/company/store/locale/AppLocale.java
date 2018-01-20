package ua.com.company.store.locale;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Владислав on 06.12.2017.
 */
public enum  AppLocale {
    EN(new Locale("en","US")),
    UA(new Locale("uk","UA"));

    private final static Locale DEFAULT_LOCALE = EN.getLocale();
    private Locale locale;

    AppLocale(Locale locale) {
       this.locale = locale;
    }

    public static Locale getDefaultLocale() {
        return DEFAULT_LOCALE;
    }

    public Locale getLocale() {
        return locale;
    }

    public static Locale forValue(String lang){
        for (final AppLocale locale : AppLocale.values()){
            if (locale.getLocale().getLanguage().equalsIgnoreCase(lang)){
                return locale.getLocale();
            }
        }
        return getDefaultLocale();
    }
    public static List<Locale> getAppLocales(){
            List<Locale> locales = new ArrayList<>();
            for (AppLocale appLocale : AppLocale.values()){
                locales.add(appLocale.getLocale());
            }
    return locales;
    }


}
