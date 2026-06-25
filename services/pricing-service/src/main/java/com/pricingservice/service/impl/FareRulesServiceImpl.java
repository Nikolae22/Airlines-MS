package com.pricingservice.service.impl;

import com.payload.request.FareRulesRequest;
import com.payload.response.FareRulesResponse;
import com.pricingservice.mapper.FareRulesMapper;
import com.pricingservice.model.Fare;
import com.pricingservice.model.FareRules;
import com.pricingservice.repository.FareRepository;
import com.pricingservice.repository.FareRulesRepository;
import com.pricingservice.service.FareRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareRulesServiceImpl implements FareRulesService {

    private final FareRulesRepository fareRulesRepository;
    private final FareRepository fareRepository;

    @Override
    public FareRulesResponse createFareRules(FareRulesRequest request) throws Exception {
        Fare fare=fareRepository.findById(request.getFareId())
                .orElseThrow(()->new Exception("Fare not found"));

        if (fareRulesRepository.existsByFareId(fare.getId())){
            throw new Exception("Fare already exists");
        }

        FareRules fareRules= FareRulesMapper.toEntity(request,fare);
        FareRules saved=fareRulesRepository.save(fareRules);
        return FareRulesMapper.toDTO(saved);
    }

    @Override
    public FareRulesResponse getFareRulesById(Long id) throws Exception {
        FareRules fareRules=fareRulesRepository.findById(id)
                .orElseThrow(()->new Exception("FareRules not found"));
        return FareRulesMapper.toDTO(fareRules);
    }

    @Override
    public FareRulesResponse getFareRulesByFareId(Long fareId) {
        FareRules fareRules=fareRulesRepository.findByFareId(fareId);
        return FareRulesMapper.toDTO(fareRules);
    }

    @Override
    public List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId) {
        return fareRulesRepository.findByAirlineId(airlineId)
                .stream()
                .map(FareRulesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception {
        FareRules fareRules=fareRulesRepository.findById(id)
                .orElseThrow(()->new Exception("FareRules not found"));
        FareRulesMapper.updateEntity(request,fareRules);
        FareRules saved=fareRulesRepository.save(fareRules);
        return FareRulesMapper.toDTO(saved);
    }

    @Override
    public void deleteFareRules(Long id) throws Exception {
        FareRules fareRules=fareRulesRepository.findById(id)
                .orElseThrow(()->new Exception("FareRules not found"));
        fareRulesRepository.delete(fareRules);

    }
}
