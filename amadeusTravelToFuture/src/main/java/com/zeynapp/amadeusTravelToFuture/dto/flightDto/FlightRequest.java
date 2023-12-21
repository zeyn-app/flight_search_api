package com.zeynapp.amadeusTravelToFuture.dto.flightDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightRequest {
    private Long departureAirportId;
    private Long arrivalAirportId;
    private LocalDateTime departureDateTime;
    private LocalDateTime returnDateTime;
    private Double price;
}
