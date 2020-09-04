package pl.xtrf.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.xtrf.weather.Const;
import pl.xtrf.weather.model.WeatherData;

@Service
public class PlaygroundService {

    @Autowired
    private RestTemplate rest;

    public ResponseEntity<String> postWeatherData(WeatherData weatherData, String weatherEndpoint){
        return rest.postForEntity(weatherEndpoint, weatherData, String.class);
    }
}
