package pl.xtrf.weather.model.city;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CityData {
    private Integer visibility;
    private Integer dt;
    private Integer timezone;
    private Integer id;
    private Integer cod;
    private String base;
    private String name;
    private Coord coord;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    private ArrayList<Weather> weather = new ArrayList<>();
}
