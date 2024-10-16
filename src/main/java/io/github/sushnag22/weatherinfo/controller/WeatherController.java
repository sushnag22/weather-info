package io.github.sushnag22.weatherinfo.controller;

import io.github.sushnag22.weatherinfo.entity.WeatherInfo;
import io.github.sushnag22.weatherinfo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherInfo> getWeatherInfo(
            @RequestParam String pincode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forDate) {
        WeatherInfo weatherInfo = weatherService.getWeatherInfo(pincode, forDate);
        return ResponseEntity.ok(weatherInfo);
    }

}
