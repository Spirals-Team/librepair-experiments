package com.tul.ta.service;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import com.tul.ta.model.weather.Weather;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.lang.String.valueOf;
import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;

@Service
public class DefaultWeatherService implements WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultWeatherService.class);

    @Override
    public Weather getWeatherByCity(String city) {
        logger.info("Trying to get weather for {}", city);
        Forecast current = new Forecast();
        try {
            YahooWeatherService service = new YahooWeatherService();
            Channel channel = service.getForecast(getWOEIDByCity(city), DegreeUnit.CELSIUS);
            current = channel.getItem().getForecasts().get(0);
        } catch (JAXBException | IOException e) {
            logger.warn("Problem while retreiving weather {}", e);
        }
        Weather weather = new Weather(current.getLow(), current.getHigh(), current.getText(), current.getDate(), city);
        logger.info("Sending weather: {}", weather);
        return weather;
    }

    @Override
    public Weather getWeatherByCityWithDate(String city, String date) {
        logger.info("Trying to get weather for {} in date {}", city, date);
        LocalDate wantedDate = parse(date);
        LocalDate currentDate = now();
        long days = ChronoUnit.DAYS.between(currentDate, wantedDate);
        //Because Weather API only gives 10 days ahead forecast
        if(days>9) {
            days = 9;
        }
        Forecast current = new Forecast();
        try {
            YahooWeatherService service = new YahooWeatherService();
            Channel channel = service.getForecast(getWOEIDByCity(city), DegreeUnit.CELSIUS);
            current = channel.getItem().getForecasts().get((int) days);
        } catch (JAXBException | IOException e) {
            logger.warn("Problem while retreiving weather {}", e);
        }
        Weather weather = new Weather(current.getLow(), current.getHigh(), current.getText(), current.getDate(), city);
        logger.info("Sending weather: {}", weather);
        return weather;
    }

    private String getWOEIDByCity(String city) {
        logger.info("Trying to get WOEID for {}", city);
        String url = "http://query.yahooapis.com/v1/public/yql?q=select*from%20geo.places%20where%20text=%22" + city + "%22&format=json";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        String woeid = "523920";
        try {
            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();
            if (httpEntity != null) {
                String retSrc = EntityUtils.toString(httpEntity);
                JSONObject result = new JSONObject(retSrc);
                JSONObject place = (JSONObject) result.getJSONObject("query").getJSONObject("results").getJSONArray("place").get(0);
                woeid = valueOf(place.get("woeid"));
            }
        } catch (IOException e) {
            logger.warn("Problem while retreiving WOEID {}", e);
        }
        return woeid;
    }
}
