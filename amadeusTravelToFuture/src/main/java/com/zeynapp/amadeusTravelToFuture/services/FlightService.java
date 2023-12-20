package com.zeynapp.amadeusTravelToFuture.services;

import com.zeynapp.amadeusTravelToFuture.dto.FlightRequest;
import com.zeynapp.amadeusTravelToFuture.dto.FlightResponse;
import com.zeynapp.amadeusTravelToFuture.exceptions.FlightException;
import com.zeynapp.amadeusTravelToFuture.models.Airport;
import com.zeynapp.amadeusTravelToFuture.models.Flight;
import com.zeynapp.amadeusTravelToFuture.repositories.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportService airportService;


    public FlightResponse create(FlightRequest flightRequest) {
        Flight flight = getFlight(flightRequest);

        checkDepartureAndArrivalAirport(flight);
        checkDepartureDateTimeAndReturnDateTime(flight);

        return getFlightResponse((flightRepository.save(flight)));
    }

    private void checkDepartureAndArrivalAirport(Flight flight) {
        if (flight.getDepartureAirport().getId().equals(flight.getArrivalAirport().getId())) {
            throw new FlightException(FlightException.DEPARTURE_AND_ARRIVAL_AIRPORT_CANNOT_BE_THE_SAME);
        }
    }

    private void checkDepartureDateTimeAndReturnDateTime(Flight flight) {
        if (!flight.getDepartureDateTime().isBefore(flight.getReturnDateTime())) {
            throw new FlightException(FlightException.DEPARTURE_DATE_CANNOT_BE_AFTER_RETURN_DATE);
        }
    }

    private Flight getFlight(FlightRequest flightRequest) {
        Airport departureAirport = airportService.findById(flightRequest.getDepartureAirportId());
        Airport arrivalAirport = airportService.findById(flightRequest.getArrivalAirportId());

        return Flight.builder()
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(flightRequest.getDepartureDateTime())
                .returnDateTime(flightRequest.getReturnDateTime())
                .price(flightRequest.getPrice())
                .build();
    }

    private FlightResponse getFlightResponse(Flight flight) {
        return FlightResponse.builder()
                .id(flight.getId())
                .departureAirport(flight.getDepartureAirport().getCity())
                .arrivalAirport(flight.getArrivalAirport().getCity())
                .departureDateTime(flight.getDepartureDateTime())
                .returnDateTime(flight.getReturnDateTime())
                .price(flight.getPrice())
                .build();
    }

    public List<FlightResponse> getAll() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(this::getFlightResponse)
                .toList();
    }
}
