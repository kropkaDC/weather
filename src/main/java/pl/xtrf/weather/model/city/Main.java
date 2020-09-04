package pl.xtrf.weather.model.city;

import lombok.Data;

@Data
public class Main {
    private Double temp;
    private Double feels_like;
    private Double temp_min;
    private Double temp_max;
    private Double pressure;
    private Double humidity;
}
