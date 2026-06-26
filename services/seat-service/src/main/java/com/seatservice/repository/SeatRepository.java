package com.seatservice.repository;

import com.seatservice.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Long> {

    boolean existsBySeatMapId(Long id);
}
