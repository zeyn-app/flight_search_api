package com.zeynapp.amadeusTravelToFuture.services;

import com.zeynapp.amadeusTravelToFuture.dto.*;
import com.zeynapp.amadeusTravelToFuture.exceptions.FlightException;
import com.zeynapp.amadeusTravelToFuture.models.Airport;
import com.zeynapp.amadeusTravelToFuture.models.Flight;
import com.zeynapp.amadeusTravelToFuture.repositories.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportService airportService;

    public List<FlightResponse> getAll() {
        List<Flight> flights = flightRepository.findAllByDepartureAirport_IsActiveTrueAndArrivalAirport_IsActiveTrue();
        return flights.stream()
                .map(this::getFlightResponse)
                .toList();
    }

    public FlightResponse create(FlightRequest flightRequest) {
        Flight flight = getFlight(flightRequest);

        checkDepartureAndArrivalAirport(flight);
        checkDepartureDateTimeAndReturnDateTime(flight);

        return getFlightResponse((flightRepository.save(flight)));
    }

    public List<SearchFlightResponse> searchOneWayFlight(String from, String to, LocalDateTime departureDateTime) {
        List<Flight> flights = flightRepository.findAllByDepartureAirport_CityAndArrivalAirport_CityAndDepartureDateTime
                (from, to, departureDateTime);

        return flights.stream()
                .map(this::getOneWayFlightResponse)
                .toList();
    }

    public List<SearchFlightResponse> searchTwoWayFlight(String from, String to, LocalDateTime departureDateTime,
                                                         LocalDateTime arrivalDateTime) {
        List<Flight> flights = flightRepository.findAllByDepartureAirport_CityAndArrivalAirport_CityAndDepartureDateTimeAndReturnDateTime(
                from, to, departureDateTime, arrivalDateTime);

        return flights.stream()
                .map(this::getTwoWayFlightResponse)
                .toList();
    }

    private SearchFlightResponse getTwoWayFlightResponse(Flight flight) {
        return TwoWayFlightResponse.builder()
                .departureAirport(flight.getDepartureAirport().getCity())
                .arrivalAirport(flight.getArrivalAirport().getCity())
                .departureDateTime(flight.getDepartureDateTime())
                .departurePrice(flight.getPrice())
                .returnAirport(flight.getArrivalAirport().getCity())
                .returnDestinationAirport(flight.getDepartureAirport().getCity())
                .returnDateTime(flight.getReturnDateTime())
                .returnPrice(flight.getPrice())
                .build();
    }



    private SearchFlightResponse getOneWayFlightResponse(Flight flight) {
        return OneWayFlightResponse.builder()
                .departureAirport(flight.getDepartureAirport().getCity())
                .arrivalAirport(flight.getArrivalAirport().getCity())
                .departureDateTime(flight.getDepartureDateTime())
                .departurePrice(flight.getPrice())
                .build();
    }

    public void delete(Long id) {
        Flight flight = findById(id);
        flight.setIsActive(false);
        flightRepository.save(flight);
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

    private Flight findById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new FlightException(FlightException.FLIGHT_NOT_FOUND));
    }
}
