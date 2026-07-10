package com.ancillaryservice.repository;

import com.ancillaryservice.model.FlightCabinAncillary;
import com.enums.AncillaryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightCabinAncillaryRepository extends JpaRepository<FlightCabinAncillary,Long> {

    List<FlightCabinAncillary> findByFlightIdAndCabinClassId(Long flightId,
                                                             Long cabinClassId);
    FlightCabinAncillary findByFlightIdAndCabinClassIdAndAncillaryType(
            Long flightId, Long cabinClassId, AncillaryType ancillaryType);

    List<FlightCabinAncillary> findAllByFlightIdAndCabinClassIdAndAncillaryType(
            Long flightId, Long cabinClassId, AncillaryType ancillaryType);
}
