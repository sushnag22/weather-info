package io.github.sushnag22.weatherinfo.repository;

import io.github.sushnag22.weatherinfo.entity.Pincode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PincodeRepository extends JpaRepository<Pincode, Long> {
    Optional<Pincode> findByPincode(String pincode);
}
