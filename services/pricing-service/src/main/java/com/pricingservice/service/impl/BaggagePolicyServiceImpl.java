package com.pricingservice.service.impl;

import com.payload.request.BaggagePolicyRequest;
import com.payload.response.BaggagePolicyResponse;
import com.pricingservice.mapper.BaggagePolicyMapper;
import com.pricingservice.model.BaggagePolicy;
import com.pricingservice.model.Fare;
import com.pricingservice.repository.BaggagePolicyRepository;
import com.pricingservice.repository.FareRepository;
import com.pricingservice.service.BaggagePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaggagePolicyServiceImpl implements BaggagePolicyService {

    private final BaggagePolicyRepository baggagePolicyRepository;
    private final FareRepository fareRepository;

    @Override
    public BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception {
        Fare fare=fareRepository.findById(request.getFareId())
                .orElseThrow(()-> new Exception("Baggage policy not found"));
        if (baggagePolicyRepository.existsByFareId(fare.getId())){
            throw new Exception("Baggage policy already exists");
        }
        BaggagePolicy baggagePolicy= BaggagePolicyMapper.toEntity(request,fare);
        BaggagePolicy saved = baggagePolicyRepository.save(baggagePolicy);
        return BaggagePolicyMapper.toDTO(saved);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception {
        BaggagePolicy baggagePolicy=baggagePolicyRepository.findById(id)
                .orElseThrow(()->new Exception("Policy not found with id"));
        return BaggagePolicyMapper.toDTO(baggagePolicy);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyByFareId(Long faireId) {
        BaggagePolicy baggagePolicy=baggagePolicyRepository.findByFareId(faireId);
        return BaggagePolicyMapper.toDTO(baggagePolicy);
    }

    @Override
    public List<BaggagePolicyResponse> getBaggagePolicyBiAirlineId(Long airlineId) {
        return baggagePolicyRepository.findByAirlineId(airlineId)
                .stream().map(
                        BaggagePolicyMapper::toDTO
                ).collect(Collectors.toList());
    }

    @Override
    public BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception {
        BaggagePolicy baggagePolicy=baggagePolicyRepository.findById(id)
                .orElseThrow(()->new Exception("Policy not found with id"));
        BaggagePolicyMapper.updateEntity(request,baggagePolicy);
        BaggagePolicy saved = baggagePolicyRepository.save(baggagePolicy);
        return BaggagePolicyMapper.toDTO(saved);
    }

    @Override
    public void deleteBaggagePolicy(Long id) throws Exception {
        BaggagePolicy baggagePolicy=baggagePolicyRepository.findById(id)
                .orElseThrow(()->new Exception("Policy not found with id"));
        baggagePolicyRepository.delete(baggagePolicy);
    }
}
