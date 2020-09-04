package pl.xtrf.weather.model.city;

import lombok.Data;

@Data
public class Weather {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}
