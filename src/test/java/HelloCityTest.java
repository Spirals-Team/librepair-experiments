import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HelloCityTest {

    private static Map<String, String> citiesNamesAndTimeZones = new HashMap<String, String>();
    private static List<String> citiesNames = new ArrayList<String>();

    @Before
    public void setUpToCaches() {

        citiesNamesAndTimeZones.put("London","Europe/London");
        citiesNamesAndTimeZones.put("Vienna","Europe/Vienna");
        citiesNamesAndTimeZones.put("Kiev","Europe/Kiev");
        citiesNamesAndTimeZones.put("Sydney","Australia/Sydney");
        citiesNamesAndTimeZones.put("Toronto","America/Toronto");
        citiesNamesAndTimeZones.put("Phoenix","America/Phoenix");
        citiesNamesAndTimeZones.put("Jujuy","America/Jujuy");
        citiesNamesAndTimeZones.put("Paramaribo","America/Paramaribo");
        citiesNamesAndTimeZones.put("Panama","America/Panama");
        citiesNamesAndTimeZones.put("Ojinaga","America/Ojinaga");

        citiesNames.add("London");
        citiesNames.add("Vienna");
        citiesNames.add("Kiev");
        citiesNames.add("Sydney");
        citiesNames.add("Toronto");
        citiesNames.add("Phoenix");
        citiesNames.add("Jujuy");
        citiesNames.add("Paramaribo");
        citiesNames.add("Panama");
        citiesNames.add("Ojinaga");

    }

    @After
    public void tearDownToCaches() {

        citiesNamesAndTimeZones.clear();
        citiesNames.clear();

    }

    private static int getTimeByTimeZoneName(String timeZoneName){
        TimeZone tz = TimeZone.getTimeZone(timeZoneName) ;
        Calendar cal = Calendar.getInstance(tz);
        DateFormat formatter = new SimpleDateFormat("HH");
        formatter.setCalendar(cal);
        formatter.setTimeZone(tz);
        return Integer.parseInt(formatter.format(cal.getTime()));
    }

    @Test
    public void testSayHelloToCityReturnWithoutSecondParameter(){

        for(String cityName: citiesNames){
            Assert.assertEquals(getTimeByTimeZoneName(citiesNamesAndTimeZones.get(cityName)),
                    HelloCity.sayHelloToCity(cityName,""));
        }

    }

    @Test
    public void testSayHelloToCityReturn(){

        for(String cityName: citiesNames){
            String timeZoneName = citiesNamesAndTimeZones.get(cityName);
            Assert.assertEquals(getTimeByTimeZoneName(timeZoneName),
                    HelloCity.sayHelloToCity(cityName,timeZoneName));
        }

    }

}
