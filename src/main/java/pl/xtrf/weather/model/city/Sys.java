package pl.xtrf.weather.model.city;

import lombok.Data;

@Data
public class Sys {
    private Integer type;
    private Integer id;
    private Double sunrise;
    private Double sunset;
    private String country;
}
