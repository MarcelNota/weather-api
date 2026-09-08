package org.example.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OpenMeteoClient {

    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search";

    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast";

    public JSONObject getWeather(String city) throws IOException {

        String encodedCity =
                URLEncoder.encode(city, StandardCharsets.UTF_8);

        String geocodingUrl = GEOCODING_URL + "?name=" + encodedCity + "&count=1" + "&language=en" + "&format=json";

        String geocodingResponse = sendGet(geocodingUrl);

        JSONObject geocodingJson = new JSONObject(geocodingResponse);

        JSONArray results = geocodingJson.optJSONArray("results");

        if (results == null || results.isEmpty()) {
            throw new RuntimeException("City not found: " + city);
        }

        JSONObject location = results.getJSONObject(0);

        double latitude = location.getDouble("latitude");

        double longitude = location.getDouble("longitude");


        String weatherUrl = FORECAST_URL + "?latitude=" + latitude + "&longitude=" + longitude + "&current=temperature_2m,wind_speed_10m";

        String weatherResponse = sendGet(weatherUrl);

        return new JSONObject(weatherResponse);
    }

    private String sendGet(String urlString) throws IOException {

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {throw new IOException("API request failed. Status code: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        connection.disconnect();

        return response.toString();
    }
}