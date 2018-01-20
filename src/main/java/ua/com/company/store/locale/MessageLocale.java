package ua.com.company.store.locale;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Владислав on 06.12.2017.
 */
public final class MessageLocale {
    public static final String BUNDLE_NAME = "/locale/messages";

    private MessageLocale() {
    }

    //Default locale
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("uk", "UA"));

    public static void setResourceBundleLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
}
