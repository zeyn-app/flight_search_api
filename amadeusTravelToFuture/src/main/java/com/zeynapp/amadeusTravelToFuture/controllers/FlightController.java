package com.zeynapp.amadeusTravelToFuture.controllers;

import com.zeynapp.amadeusTravelToFuture.dto.flightDto.FlightRequest;
import com.zeynapp.amadeusTravelToFuture.dto.flightDto.FlightResponse;
import com.zeynapp.amadeusTravelToFuture.dto.flightDto.FlightUpdateRequest;
import com.zeynapp.amadeusTravelToFuture.dto.flightDto.SearchFlightResponse;
import com.zeynapp.amadeusTravelToFuture.services.FlightService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("v1/flights")
@AllArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public List<FlightResponse> getAll() {
        return flightService.getAll();
    }

    @GetMapping("/search")
    public List<SearchFlightResponse> search(@RequestParam(value = "from") String from,
                                             @RequestParam(value = "to") String to,
                                             @RequestParam(value = "departureDate") LocalDateTime departureDateTime,
                                             @RequestParam(value = "returnDate", required = false) LocalDateTime returnDateTime) {
        if (returnDateTime == null) {
            return flightService.searchOneWayFlight(from, to, departureDateTime);
        } else {
            return flightService.searchTwoWayFlight(from, to, departureDateTime, returnDateTime);
        }
    }

    @PostMapping
    public FlightResponse create(@Valid @RequestBody FlightRequest flightRequest) {
        return flightService.create(flightRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        flightService.delete(id);
        System.out.println("Airport removed successfully");
    }

    // update
    @PutMapping("/update/{id}")
    public FlightResponse update( @PathVariable Long id, @RequestBody FlightUpdateRequest flightUpdateRequest){
        return flightService.update(id, flightUpdateRequest);
    }
}
