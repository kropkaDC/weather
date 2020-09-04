package pl.xtrf.weather.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WeatherData {
    private String currentWeather;
    private BigDecimal currentTemperature;
    private Long timestamp;
    private String sunrise;
    private String sunset;
}
