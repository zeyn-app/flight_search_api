package com.zeynapp.amadeusTravelToFuture.dto.flightDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class FlightUpdateRequest {
    private LocalDateTime departureDateTime;
    private LocalDateTime returnDateTime;
    private Double price;
}