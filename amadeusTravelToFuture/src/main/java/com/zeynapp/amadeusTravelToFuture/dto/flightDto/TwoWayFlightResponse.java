package com.zeynapp.amadeusTravelToFuture.dto.flightDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class TwoWayFlightResponse extends SearchFlightResponse{
    @Builder.Default
    private String returnInfo = "RETURN INFORMATION";
    private String returnAirport;
    private String returnDestinationAirport;
    private LocalDateTime returnDateTime;
    private Double returnPrice;
}
