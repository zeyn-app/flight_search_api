package com.zeynapp.amadeusTravelToFuture.repositories;

import com.zeynapp.amadeusTravelToFuture.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByDepartureAirport_IsActiveTrueAndArrivalAirport_IsActiveTrue();

    List<Flight> findAllByDepartureAirport_CityAndArrivalAirport_CityAndDepartureDateTime(String from, String to,
                                                                                          LocalDateTime dateTime);
    List<Flight> findAllByDepartureAirport_CityAndArrivalAirport_CityAndDepartureDateTimeAndReturnDateTime(
            String from, String to, LocalDateTime departureDate, LocalDateTime returnDate);
}
