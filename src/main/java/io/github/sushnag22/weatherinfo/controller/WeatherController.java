package io.github.sushnag22.weatherinfo.controller;

import io.github.sushnag22.weatherinfo.entity.WeatherInfo;
import io.github.sushnag22.weatherinfo.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {

    // Logger to log the events
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    // Service to get the weather info
    private final WeatherService weatherService;

    // Constructor based dependency injection
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // API to get the weather info for a given pincode and date
    @Operation(summary = "Get weather info",
            description = "Get weather info for a given Indian pincode and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather info retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation =  Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getWeatherInfo(@RequestParam String pincode,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forDate) {

        try {

            // Get the weather info
            WeatherInfo weatherInfo = weatherService.getWeatherInfo(pincode, forDate);

            // Return the weather info
            return ResponseEntity.ok().body(Map.of(
                    "status", "Success",
                    "statusCode", 200,
                    "message", "Weather info retrieved successfully",
                    "result", weatherInfo
            ));
        } catch (Exception exception) {

            // Log the error
            logger.error("Failed to get weather info for pincode: {} and date:{}", pincode, forDate, exception);

            // Return an internal server error response
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "statusCode", 500,
                    "message", ""
            ));
        }
    }

}
