package com.flightopsservice.repository;

import com.flightopsservice.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule,Long> {

    List<FlightSchedule> findByFlightAirlineId(Long airlineId);
}
