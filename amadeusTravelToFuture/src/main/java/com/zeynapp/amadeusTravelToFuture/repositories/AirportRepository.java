package com.zeynapp.amadeusTravelToFuture.repositories;

import com.zeynapp.amadeusTravelToFuture.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsAirportByCity(String city);
    Optional<Airport> findAirportByCity(String city);
}
