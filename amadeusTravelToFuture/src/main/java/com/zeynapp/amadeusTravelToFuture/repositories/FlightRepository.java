package com.zeynapp.amadeusTravelToFuture.repositories;

import com.zeynapp.amadeusTravelToFuture.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
