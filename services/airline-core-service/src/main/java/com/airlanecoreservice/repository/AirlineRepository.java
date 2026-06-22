package com.airlanecoreservice.repository;

import com.airlanecoreservice.model.Aircraft;
import com.airlanecoreservice.model.Airline;
import com.enums.AirlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline,Long> {

    Optional<Airline> findByOwnerId(Long ownerId);

    List<Airline> findByStatus(AirlineStatus status);
}
