package com.zeynapp.amadeusTravelToFuture.dto.flightDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class FlightResponse {
    private Long id;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDateTime;
    private LocalDateTime returnDateTime;
    private Double price;
}
