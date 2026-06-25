package com.pricingservice.repository;

import com.pricingservice.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FareRepository extends JpaRepository<Fare,Long> {

    boolean existsByFlightIdAndCabinClassIdAndName(Long flightId,
                                                   Long cabinClassId,
                                                   String name);

    List<Fare> findByFlightIdAndCabinClassId(Long flightId,
                                             Long cabinClassId);

    boolean existsByFlightIdAndCabinClassIdAndNameAndIdNot(Long flightId,
                                                           Long cabinClassId,
                                                           String name,
                                                           Long id);

    List<Fare> findByFlightIdInAndCabinClassId(List<Long> flightsIds, Long cabinClassId);
}
