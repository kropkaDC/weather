package pl.xtrf.weather;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherServiceTest {

    @Value("${openweatherapi.city}")
    private String city;
    @Value("${openweatherapi.countrycode}")
    private String code;
    @Value("${openweatherapi.key}")
    private String key;
    @Value("${openweatherapi.endpoint}")
    private String cityEndpoint;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void statusCode(){
        Map<String, String> urlVariables = Map.of("city", city, "code", code, "key", key);
        ResponseEntity<Object> response = restTemplate.getForEntity(cityEndpoint, Object.class,  urlVariables);
        Assert.assertNotNull(response);
        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
