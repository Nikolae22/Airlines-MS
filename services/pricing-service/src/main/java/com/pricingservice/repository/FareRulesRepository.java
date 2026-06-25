package com.pricingservice.repository;

import com.pricingservice.model.FareRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FareRulesRepository extends JpaRepository<FareRules,Long> {

    FareRules findByFareId(Long fareId);
    List<FareRules> findByAirlineId(Long airlineId);
    boolean existsByFareId(Long fareId);

}
