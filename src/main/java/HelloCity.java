import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HelloCity {
    private static Logger log = Logger.getLogger(HelloCity.class.getName());

    public static int sayHelloToCity(String cityName, String timeZoneName) {
        log.info("starting helloCity method for city: " + cityName);
        TimeZone timezone;
        if (timeZoneName == null || timeZoneName.length() == 0) {
            String[] timeZoneNames = TimeZone.getAvailableIDs();
            String resultTimeZone = "";
            if (cityName != null) {
                String cityNameNoSpaces = cityName.replace(' ', '_');
                for (String tzn : timeZoneNames) {
                    if (tzn.contains(cityNameNoSpaces)) {
                        resultTimeZone = tzn;
                        break;
                    }
                }
            }
            //if city was not found in timezones array use GMT+0
            timezone = TimeZone.getTimeZone(resultTimeZone);
        } else {
            timezone = TimeZone.getTimeZone(timeZoneName);
        }

        Calendar calendar = Calendar.getInstance(timezone);
        DateFormat formatter = new SimpleDateFormat("HH");
        formatter.setCalendar(calendar);
        formatter.setTimeZone(timezone);

        ResourceBundle myBundle = ResourceBundle.getBundle("MyLabels");

        int currentHour = Integer.parseInt(formatter.format(calendar.getTime()));

        log.info("foundTime in this city is " + currentHour);
        String helloString;

        if (currentHour >= 6 && currentHour < 9) {
            helloString = myBundle.getString("good_morning");
        } else if (currentHour >= 9 && currentHour < 19) {
            helloString = myBundle.getString("good_afternoon");
        } else if (currentHour >= 19 && currentHour < 23) {
            helloString = myBundle.getString("good_evening");
        } else {
            helloString = myBundle.getString("good_night");
        }
        log.info("The result message for HelloCityMethod is " + helloString);
        try {
            System.out.println(
                    new String(helloString.getBytes("ISO-8859-5"), "UTF-8") + " " + cityName + "!");
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            e.printStackTrace();
        }

        return currentHour;

    }

    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(
                    HelloCity.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        Scanner scn = new Scanner(System.in);
        String cityName = scn.nextLine();
        String timeZoneName = scn.nextLine();
        sayHelloToCity(cityName, timeZoneName);

    }
}


