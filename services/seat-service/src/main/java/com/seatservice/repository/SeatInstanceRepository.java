package com.seatservice.repository;

import com.seatservice.model.SeatInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatInstanceRepository extends JpaRepository<SeatInstance,Long> {
}
