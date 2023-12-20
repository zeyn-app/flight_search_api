package com.zeynapp.amadeusTravelToFuture.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AirportResponse {
    private Long id;
    private String city;
}
