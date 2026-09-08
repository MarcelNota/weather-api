package org.example.service;

import org.example.client.OpenMeteoClient;
import org.example.entity.Weather;
import org.example.repository.WeatherRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    private final WeatherRepository repository;
    private final OpenMeteoClient client;

    public WeatherService(
            WeatherRepository repository,
            OpenMeteoClient client) {

        this.repository = repository;
        this.client = client;
    }

    public Weather create(String city) throws Exception {

        JSONObject json = client.getWeather(city);

        Weather weather = new Weather(json.getLong("locationId"),
                        json.getString("name"),
                        json.getDouble("latitude"),
                        json.getDouble("longitude"),
                        json.getString("country"),
                        json.getString("countryCode"),
                        json.getString("timezone"),
                        json.getString("currentTime"),
                        json.getDouble("temperature")
        );

        return repository.save(weather);
    }

    public List<Weather> getAll() {
        return repository.findAll();
    }

    public Weather getById(Long id) {

        return repository.findById(id).orElseThrow(() -> new RuntimeException("Weather not found"));
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }
}