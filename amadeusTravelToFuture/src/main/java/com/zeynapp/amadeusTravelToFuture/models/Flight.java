package com.zeynapp.amadeusTravelToFuture.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "departureAirportId")
    private Airport departureAirport;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "arrivalAirportId")
    private Airport arrivalAirport;

    private LocalDateTime departureDateTime;
    private LocalDateTime returnDateTime;
    private Double price;

}
