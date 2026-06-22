package com.airlanecoreservice.repository;

import com.airlanecoreservice.model.Aircraft;
import com.airlanecoreservice.model.Airline;
import com.payload.response.AircraftResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft,Long> {

    List<Aircraft> findByAirlineId(Long airlineId);
    Boolean existsByCode(String code);
    Aircraft findByIdAndAirlineId(Long id, Long airlineId);

}
