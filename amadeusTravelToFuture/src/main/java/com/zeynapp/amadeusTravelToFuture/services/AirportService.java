package com.zeynapp.amadeusTravelToFuture.services;

import com.zeynapp.amadeusTravelToFuture.dto.AirportRequest;
import com.zeynapp.amadeusTravelToFuture.dto.AirportResponse;
import com.zeynapp.amadeusTravelToFuture.models.Airport;
import com.zeynapp.amadeusTravelToFuture.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public Airport findById(Long id){
        return airportRepository.findById(id).orElseThrow();// add exception
    }

    public AirportResponse create(AirportRequest airportRequest) {
        Airport airport = Airport.builder()
                .city(airportRequest.getCity())
                .build();

        return getAirportResponse(airportRepository.save(airport));
    }

    public List<AirportResponse> getAll() {
        List<Airport> airports = airportRepository.findAll();

        return airports.stream()
                .map(this::getAirportResponse)
                .toList();
    }

    private AirportResponse getAirportResponse(Airport airport){
        return AirportResponse.builder()
                .id(airport.getId())
                .city(airport.getCity())
                .build();
    }
}
