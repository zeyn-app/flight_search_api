package com.zeynapp.amadeusTravelToFuture.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
public class SearchFlightResponse {
    @Builder.Default
    private String departureInfo = "DEPARTURE INFORMATION";
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDateTime;
    private Double departurePrice;
}
