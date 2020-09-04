package pl.xtrf.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.xtrf.weather.model.city.CityData;

import java.util.Map;

@Service
public class OpenWeatherMapService {

    @Autowired
    private RestTemplate rest;

    public ResponseEntity<CityData> getCityData(String city, String code, String key, String cityEndpoint) {
        Map<String, String> urlVariables = Map.of("city", city, "code", code, "key", key);
        ResponseEntity<CityData> response = rest.getForEntity(cityEndpoint, CityData.class, urlVariables);

        return response;
    }
}
