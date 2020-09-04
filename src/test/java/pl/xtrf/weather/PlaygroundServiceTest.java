package pl.xtrf.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import pl.xtrf.weather.model.WeatherData;
import pl.xtrf.weather.service.PlaygroundService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static pl.xtrf.weather.Const.DATE_FORMAT;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaygroundServiceTest {

    @Autowired
    private PlaygroundService playgroundService;
    @Autowired
    private WeatherScheduler scheduler;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${playground.endpoint}")
    private String weatherEndpoint;

    @Test
    public void testJSON(){

        Date date = new Date();
        String jsStr = null, json = "{" +
                "\"currentWeather\": \"cos\"," +
                "\"currentTemperature\": 2.14," +
                "\"timestamp\": #{timestamp}," +
                "\"sunrise\": \"#{date}\"," +
                "\"sunset\": \"#{date}\"" +
                "}";

        json = json
                .replace("#{date}", new SimpleDateFormat(DATE_FORMAT).format(date))
                .replace("#{timestamp}", Long.toString(new Timestamp(date.getTime()).getTime()));

        WeatherData weatherData = createWeatherData(date);
        ObjectMapper obj = new ObjectMapper();

        try {
            jsStr = obj.writeValueAsString(weatherData);
            System.out.println(jsStr);

            assertTrue(json.replaceAll("\\s+","").equalsIgnoreCase(jsStr.replaceAll("\\s+","")));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost() {
        WeatherData weatherData = createWeatherData(new Date());
        ResponseEntity<String> result = playgroundService.postWeatherData(weatherData, weatherEndpoint);

        assertNotNull(result);
        assertSame(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), "Hello World!");

    }

    private WeatherData createWeatherData(Date date){
        WeatherData weatherData = new WeatherData();
        weatherData.setCurrentWeather("cos");
        weatherData.setCurrentTemperature(BigDecimal.valueOf(2.14));
        weatherData.setSunrise(new SimpleDateFormat(DATE_FORMAT).format(date));
        weatherData.setSunset(new SimpleDateFormat(DATE_FORMAT).format(date));
        weatherData.setTimestamp(new Timestamp(date.getTime()).getTime());

        return weatherData;
    }
}
