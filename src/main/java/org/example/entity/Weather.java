package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long locationId;
    private String name;
    private Double latitude;
    private Double longitude;
    private String country;
    private String countryCode;
    private String timezone;

    @Column(name = "weather_current_time")
    private String currentTime;

    private Double temperature;

    public Weather() {
    }

    public Weather(
            Long locationId,
            String name,
            Double latitude,
            Double longitude,
            String country,
            String countryCode,
            String timezone,
            String currentTime,
            Double temperature) {

        this.locationId = locationId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.countryCode = countryCode;
        this.timezone = timezone;
        this.currentTime = currentTime;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public Double getTemperature() {
        return temperature;
    }
}