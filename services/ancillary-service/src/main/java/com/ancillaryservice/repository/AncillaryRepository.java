package com.ancillaryservice.repository;

import com.ancillaryservice.model.Ancillary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AncillaryRepository extends JpaRepository<Ancillary,Long> {

    List<Ancillary> findByAirlineId(Long airlineId);
}
