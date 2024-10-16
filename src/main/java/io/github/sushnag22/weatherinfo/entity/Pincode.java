package io.github.sushnag22.weatherinfo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pincode")
@Getter
@Setter
public class Pincode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String pincode;

    private String area;

    private String city;

    private String state;

    private Double latitude;

    private Double longitude;

}
