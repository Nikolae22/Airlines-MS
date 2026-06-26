package com.seatservice.repository;

import com.seatservice.model.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatMapRepository extends JpaRepository<SeatMap,Long> {

    SeatMap findByCabinClassId(Long cabinClassId);
    boolean existsByAirlineIdAndCabinClassIdAndName(Long airlineId, Long cabinId, String name);
}
