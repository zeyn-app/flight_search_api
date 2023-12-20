package com.zeynapp.amadeusTravelToFuture.repositories;

import com.zeynapp.amadeusTravelToFuture.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
