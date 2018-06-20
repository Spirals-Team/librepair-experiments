package com.tul.ta.controller;

import com.tul.ta.model.weather.Weather;
import com.tul.ta.service.DefaultWeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static java.time.LocalDate.parse;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultWeatherService weatherService;

    @Test
    public void whenGetWeatherForWarsawShouldReturnJsonObect() throws Exception {
        Weather weather = new Weather(20, 25, "Sunny", new Date(), "Warsaw");
        given(weatherService.getWeatherByCity("Warsaw")).willReturn(weather);
        mockMvc.perform(get("/api/weather/Warsaw")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lowTemperature", is(weather.getLowTemperature())))
                .andExpect(jsonPath("$.highTemperature", is(weather.getHighTemperature())))
                .andExpect(jsonPath("$.description", is(weather.getDescription())))
                .andExpect(jsonPath("$.date", is(weather.getDate())))
                .andExpect(jsonPath("$.city", is(weather.getCity())));
    }

    @Test
    public void whenGetWeatherForWarsawWithDateShouldReturnJsonObect() throws Exception {
        String date = "2018-06-22";
        LocalDate wantedDate = parse(date);
        Weather weather = new Weather(20, 25, "Sunny",  Date.from(wantedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), "Warsaw");
        given(weatherService.getWeatherByCityWithDate("Warsaw", date)).willReturn(weather);
        mockMvc.perform(get("/api/weather/Warsaw?date=2018-06-22")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lowTemperature", is(weather.getLowTemperature())))
                .andExpect(jsonPath("$.highTemperature", is(weather.getHighTemperature())))
                .andExpect(jsonPath("$.description", is(weather.getDescription())))
                .andExpect(jsonPath("$.date", is(weather.getDate())))
                .andExpect(jsonPath("$.city", is(weather.getCity())));
    }
}
