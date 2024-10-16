package io.github.sushnag22.weatherinfo.repository;

import io.github.sushnag22.weatherinfo.entity.Pincode;
import io.github.sushnag22.weatherinfo.entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
    Optional<WeatherInfo> findByPincodeAndDate(Pincode pincode, LocalDate date);
}
