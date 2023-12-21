package com.zeynapp.amadeusTravelToFuture.controllers;

import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportRequest;
import com.zeynapp.amadeusTravelToFuture.dto.airportDto.AirportResponse;
import com.zeynapp.amadeusTravelToFuture.services.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;
    @GetMapping
    public List<AirportResponse> getAll(){
        return airportService.getAll();
    }
    @PostMapping("/create")
    public ResponseEntity<AirportResponse> create(@RequestBody AirportRequest airportRequest){
        AirportResponse airportResponse = airportService.create(airportRequest);
        return ResponseEntity.ok(airportResponse);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Long id){
        airportService.delete(id);
        System.out.println("Airport removed successfully");
    }

}