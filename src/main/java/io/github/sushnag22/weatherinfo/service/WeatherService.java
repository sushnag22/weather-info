package io.github.sushnag22.weatherinfo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sushnag22.weatherinfo.entity.Pincode;
import io.github.sushnag22.weatherinfo.entity.WeatherInfo;
import io.github.sushnag22.weatherinfo.repository.PincodeRepository;
import io.github.sushnag22.weatherinfo.repository.WeatherInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {

    private final PincodeRepository pincodeRepository;
    private final WeatherInfoRepository weatherInfoRepository;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${openweathermap.api.key}")
    private String OPENWEATHERMAP_API_KEY;

    @Value("${openweathermap.api.url}")
    private String OPENWEATHERMAP_API_URL;

    @Value("${rapidapi.api.key}")
    private String RAPIDAPI_API_KEY;

    @Value("${rapidapi.api.host}")
    private String RAPIDAPI_API_HOST;

    @Value("${rapidapi.api.url}")
    private String RAPIDAPI_API_URL;

    @Autowired
    public WeatherService(PincodeRepository pincodeRepository, WeatherInfoRepository weatherInfoRepository,
                          RestTemplate restTemplate) {
        this.pincodeRepository = pincodeRepository;
        this.weatherInfoRepository = weatherInfoRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherInfo getWeatherInfo(String pincode, LocalDate date) {

        // Get the Pincode entity
        Pincode pincodeEntity = getPincodeEntity(pincode);

        // Check if weather info already exists in the database
        Optional<WeatherInfo> existingWeatherInfo = weatherInfoRepository.findByPincodeAndDate(pincodeEntity, date);

        // Return the existing weather info if found
        if (existingWeatherInfo.isPresent()) {
            return existingWeatherInfo.get();
        }

        // Fetch weather data from OpenWeatherMap API
        String url = String.format(OPENWEATHERMAP_API_URL,
                pincodeEntity.getLatitude(), pincodeEntity.getLongitude(), OPENWEATHERMAP_API_KEY);

        try {

            // Make a GET request to the OpenWeatherMap API
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String responseBody = response.getBody();

            // Parse the API response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Extract the necessary data from the response
            double temperature = jsonNode.get("main").get("temp").asDouble();
            double humidity = jsonNode.get("main").get("humidity").asDouble();
            String description = jsonNode.get("weather").get(0).get("description").asText();

            // Create and populate a new weather info entity
            WeatherInfo newWeatherInfo = new WeatherInfo();
            newWeatherInfo.setPincode(pincodeEntity);
            newWeatherInfo.setDate(date);
            newWeatherInfo.setTemperature(temperature);
            newWeatherInfo.setHumidity(humidity);
            newWeatherInfo.setDescription(description);

            // Save and return the new weather info entity
            return weatherInfoRepository.save(newWeatherInfo);

        } catch (Exception exception) {

            // Log the error and return an empty weather info entity
            logger.error("Error while fetching weather data for pincode: {} and date: {}", pincode, date, exception);

            // Return an empty weather info entity
            return new WeatherInfo();
        }
    }

    private Pincode getPincodeEntity(String pincode) {

        // Check if the Pincode entity already exists in the database and return it
        return pincodeRepository.findByPincode(pincode)
                .orElseGet(() -> fetchPincodeFromApi(pincode));
    }

    private Pincode fetchPincodeFromApi(String pincode) {
        try {

            // Make a GET request to the RapidAPI to fetch pincode data
            String url = RAPIDAPI_API_URL + pincode;
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-key", RAPIDAPI_API_KEY);
            headers.set("x-rapidapi-host", RAPIDAPI_API_HOST);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();

            // Parse the API response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Extract data
            String area = jsonNode.get(0).get("area").asText();
            double latitude = jsonNode.get(0).get("lat").asDouble();
            double longitude = jsonNode.get(0).get("lng").asDouble();
            String district = jsonNode.get(0).get("district").asText();
            String state = jsonNode.get(0).get("state").asText();

            // Create and save the new Pincode entity
            Pincode newPincode = new Pincode();
            newPincode.setPincode(pincode);
            newPincode.setArea(area);
            newPincode.setCity(district);
            newPincode.setState(state);
            newPincode.setLatitude(latitude);
            newPincode.setLongitude(longitude);

            // Save and return the new Pincode entity
            return pincodeRepository.save(newPincode);

        } catch (Exception exception) {

            // Log the error and return an empty Pincode entity
            logger.error("Error while fetching pincode data from RapidAPI for pincode: {}", pincode, exception);

            // Return an empty Pincode entity
            return new Pincode();
        }
    }
}
