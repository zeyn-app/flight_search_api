package com.zeynapp.amadeusTravelToFuture.services;

import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportRequest;
import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportResponse;
import com.zeynapp.amadeusTravelToFuture.exceptions.AirportException;
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
        return airportRepository.findById(id).orElseThrow(()-> new AirportException(AirportException.AIRPORT_NOT_FOUND));
    }

    public AirportResponse create(AirportRequest airportRequest) {
        checkIfAirportExists(airportRequest);

        Airport airport = Airport.builder()
                .city(airportRequest.getCity())
                .build();

        return getAirportResponse(airportRepository.save(airport));
    }

    private void checkIfAirportExists(AirportRequest airportRequest) {
        if(airportRepository.existsAirportByCity(airportRequest.getCity())){
            throw new AirportException(AirportException.AIRPORT_EXISTS);
        }
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

    public void delete(Long id) {
        Airport airport = findById(id);
        airport.setIsActive(false);
        airportRepository.save(airport);
    }
}
