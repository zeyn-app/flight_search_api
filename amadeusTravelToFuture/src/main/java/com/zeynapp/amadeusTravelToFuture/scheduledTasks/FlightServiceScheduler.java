package com.zeynapp.amadeusTravelToFuture.scheduledTasks;

import com.zeynapp.amadeusTravelToFuture.models.Airport;
import com.zeynapp.amadeusTravelToFuture.models.Flight;
import com.zeynapp.amadeusTravelToFuture.repositories.AirportRepository;
import com.zeynapp.amadeusTravelToFuture.repositories.FlightRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FlightServiceScheduler {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightServiceScheduler(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    public void syncFlightData() {
        Airport istanbul = getOrCreateAirport("Istanbul");
        Airport ankara = getOrCreateAirport("Ankara");

        LocalDateTime departureDateTime = LocalDateTime.now();
        LocalDateTime returnDateTime = departureDateTime.plusHours(2);

        Flight newFlight = Flight.builder()
                .arrivalAirport(ankara)
                .departureAirport(istanbul)
                .departureDateTime(departureDateTime)
                .returnDateTime(returnDateTime)
                .price(500.00)
                .build();

        flightRepository.save(newFlight);
    }

    private Airport getOrCreateAirport(String city) {
        Optional<Airport> airportOptional = airportRepository.findAirportByCity(city);
        return airportOptional.orElseGet(() -> airportRepository.save(Airport.builder().city(city).build()));
    }
}
