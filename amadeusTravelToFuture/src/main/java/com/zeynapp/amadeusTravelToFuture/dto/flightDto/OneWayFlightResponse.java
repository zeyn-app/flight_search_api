package com.zeynapp.amadeusTravelToFuture.dto.flightDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class OneWayFlightResponse extends SearchFlightResponse{
}
