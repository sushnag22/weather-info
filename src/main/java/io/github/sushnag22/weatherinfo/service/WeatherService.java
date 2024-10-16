package io.github.sushnag22.weatherinfo.service;

import io.github.sushnag22.weatherinfo.entity.Pincode;
import io.github.sushnag22.weatherinfo.entity.WeatherInfo;
import io.github.sushnag22.weatherinfo.repository.PincodeRepository;
import io.github.sushnag22.weatherinfo.repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {

    private final PincodeRepository pincodeRepository;

    private final WeatherInfoRepository weatherInfoRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(PincodeRepository pincodeRepository, WeatherInfoRepository weatherInfoRepository,
                          RestTemplate restTemplate) {
        this.pincodeRepository = pincodeRepository;
        this.weatherInfoRepository = weatherInfoRepository;
        this.restTemplate = restTemplate;
    }

    @Value("${openweathermap.api.key")
    private String apiKey;

    public WeatherInfo getWeatherInfo(String pincode, LocalDate date) {
        Pincode pincodeEntity = getPincodeEntity(pincode);
        Optional<WeatherInfo> existingWeatherInfo = weatherInfoRepository.findByPincodeAndDate(pincodeEntity, date);

        if (existingWeatherInfo.isPresent()) {
            return existingWeatherInfo.get();
        }

        // Fetch weather data from OpenWeatherMap API
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s",
                pincodeEntity.getLatitude(), pincodeEntity.getLongitude(), apiKey);

        // Make API call and parse response
        // Create and save new WeatherInfo entity
        // Return the new WeatherInfo

        // Note: Implement the actual API call and response parsing here
        return new WeatherInfo(); // Placeholder return
    }

    private Pincode getPincodeEntity(String pincode) {
        Optional<Pincode> existingPincode = pincodeRepository.findByPincode(pincode);

        if (existingPincode.isPresent()) {
            return existingPincode.get();
        }

        // Fetch lat/long from Geocoding API
        // Create and save new Pincode entity
        // Return the new Pincode

        // Note: Implement the actual Geocoding API call here
        return new Pincode(); // Placeholder return
    }

}
