package com.zeynapp.amadeusTravelToFuture.controllers;

import com.zeynapp.amadeusTravelToFuture.dto.FlightRequest;
import com.zeynapp.amadeusTravelToFuture.dto.FlightResponse;
import com.zeynapp.amadeusTravelToFuture.services.FlightService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/flights")
@AllArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public FlightResponse create(@Valid @RequestBody FlightRequest flightRequest){
        return flightService.create(flightRequest);
    }

    @GetMapping
    public List<FlightResponse> getAll(){
        return flightService.getAll();
    }

    // delete
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        flightService.delete(id);
        System.out.println("Airport removed successfully");
    }

    // update

}
