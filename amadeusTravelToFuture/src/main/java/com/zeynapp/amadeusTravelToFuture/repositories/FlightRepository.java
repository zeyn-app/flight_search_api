package com.zeynapp.amadeusTravelToFuture.repositories;

import com.zeynapp.amadeusTravelToFuture.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByDepartureAirport_IsActiveTrueAndArrivalAirport_IsActiveTrue();
}
