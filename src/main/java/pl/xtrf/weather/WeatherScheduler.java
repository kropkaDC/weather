package pl.xtrf.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.xtrf.weather.model.city.CityData;
import pl.xtrf.weather.model.WeatherData;
import pl.xtrf.weather.service.OpenWeatherMapService;
import pl.xtrf.weather.service.PlaygroundService;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.xtrf.weather.Const.DATE_FORMAT;

@Slf4j
@Component
public class WeatherScheduler {

    @Autowired
    private OpenWeatherMapService weatherMapService;

    @Autowired
    private PlaygroundService playgroundService;

    @Value("${openweatherapi.city}")
    private String city;

    @Value("${openweatherapi.countrycode}")
    private String code;

    @Value("${openweatherapi.key}")
    private String key;

    @Value("${openweatherapi.endpoint}")
    private String cityEndpoint;

    @Value("${playground.endpoint}")
    private String weatherEndpoint;

    public WeatherScheduler() {
    }

    @Scheduled(cron = "${timer}")
    public void weatherProcessing() throws Exception {
        CityData cityData = weatherMapService.getCityData(city, code, key, cityEndpoint).getBody();
        WeatherData weatherData = createWeatherData(cityData);

        log.info(cityData.toString());
        log.info(new ObjectMapper().writeValueAsString(weatherData));

        ResponseEntity<String> result = playgroundService.postWeatherData(weatherData, weatherEndpoint);

        if (result != null) {
            log.info("Response: " + result.getBody() + " Status: " + result.getStatusCode());
        }
    }

    private WeatherData createWeatherData(CityData cityData) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCurrentWeather(cityData.getWeather().get(0).getMain());
        weatherData.setCurrentTemperature(kelwinToC(cityData.getMain().getTemp()));
        weatherData.setSunrise(longDateToStr(cityData.getSys().getSunrise().longValue() * 1000));
        weatherData.setSunset(longDateToStr(cityData.getSys().getSunset().longValue() * 1000));
        weatherData.setTimestamp(new Timestamp(new Date().getTime()).getTime());

        return weatherData;
    }

    private String longDateToStr(long longDate) {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date(longDate));
    }

    private BigDecimal kelwinToC(Double temp) {
        return BigDecimal.valueOf(temp - 273.15).setScale(2, RoundingMode.HALF_UP);
    }
}
