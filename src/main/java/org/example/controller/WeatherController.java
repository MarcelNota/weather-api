package org.example.controller;

import org.example.entity.Weather;
import org.example.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }


    @PostMapping
    public Weather create(@RequestParam String city)
            throws Exception {

        return service.create(city);
    }


    @GetMapping
    public List<Weather> getAll() {

        return service.getAll();
    }


    @GetMapping("/{id}")
    public Weather getById(
            @PathVariable Long id) {

        return service.getById(id);
    }


    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id) {

        service.delete(id);

        return "Weather deleted successfully";
    }
}