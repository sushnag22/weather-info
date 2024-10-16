package io.github.sushnag22.weatherinfo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "weather_info")
@Getter
@Setter
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pincode_id", nullable = false)
    private Pincode pincode;

    @Column(nullable = false)
    private LocalDate date;

    private Double temperature;

    private Double humidity;

    private String description;

}
