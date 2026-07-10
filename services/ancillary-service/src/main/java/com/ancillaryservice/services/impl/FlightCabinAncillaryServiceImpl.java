package com.ancillaryservice.services.impl;

import com.ancillaryservice.mapper.FlightCabinAncillaryMapper;
import com.ancillaryservice.mapper.InsuranceCoverageMapper;
import com.ancillaryservice.model.Ancillary;
import com.ancillaryservice.model.FlightCabinAncillary;
import com.ancillaryservice.model.InsuranceCoverage;
import com.ancillaryservice.repository.AncillaryRepository;
import com.ancillaryservice.repository.FlightCabinAncillaryRepository;
import com.ancillaryservice.repository.InsuranceCoverageRepository;
import com.ancillaryservice.services.FlightCabinAncillaryService;
import com.enums.AncillaryType;
import com.payload.request.FlightCabinAncillaryRequest;
import com.payload.request.FlightInstanceCabinRequest;
import com.payload.response.FlightCabinAncillaryResponse;
import com.payload.response.InsuranceCoverageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightCabinAncillaryServiceImpl implements FlightCabinAncillaryService {

    private final FlightCabinAncillaryRepository flightCabinAncillaryRepository;
    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public FlightCabinAncillaryResponse create(FlightCabinAncillaryRequest request) throws Exception {
        Ancillary ancillary=ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(()->new Exception("Ancillary not found"));
        FlightCabinAncillary flightCabinAncillary=FlightCabinAncillary.builder()
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .ancillary(ancillary)
                .available(request.getAvailable())
                .maxQuantity(request.getMaxQuantity())
                .price(request.getPrice())
                .includedInFare(request.getIncludedInFare())
                .build();

        FlightCabinAncillary saved = flightCabinAncillaryRepository.save(flightCabinAncillary);
        return convertToResponse(saved);
    }

    @Override
    public FlightCabinAncillaryResponse getById(Long id) throws Exception {
        FlightCabinAncillary flightCabinAncillary=flightCabinAncillaryRepository.findById(id)
                .orElseThrow(()->new Exception("FlightCabinAncillary non trovato"));
        return convertToResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flightId, Long cabinClassId) {
        return flightCabinAncillaryRepository.findByFlightIdAndCabinClassId(flightId,cabinClassId)
                .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids) {
        return flightCabinAncillaryRepository.findAllById(ids)
                .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndType(Long flightId, Long cabinClassId, AncillaryType type) {
        FlightCabinAncillary flightCabinAncillaryResponse = flightCabinAncillaryRepository.findByFlightIdAndCabinClassIdAndAncillaryType(
                flightId, cabinClassId, type
        );
        return convertToResponse(flightCabinAncillaryResponse);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndType(Long flightId, Long cabinClassId, AncillaryType type) {
        return flightCabinAncillaryRepository.findAllByFlightIdAndCabinClassIdAndAncillaryType(
                flightId,cabinClassId,type
        ).stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public FlightCabinAncillaryResponse update(Long id, FlightCabinAncillaryRequest request) throws Exception {
        FlightCabinAncillary flightCabinAncillary=flightCabinAncillaryRepository.findById(id)
                .orElseThrow(()->new Exception("FlightCabinAncillary non trovato"));
        flightCabinAncillary.setAvailable(request.getAvailable());
        flightCabinAncillary.setMaxQuantity(request.getMaxQuantity());
        flightCabinAncillary.setPrice(request.getPrice());
        flightCabinAncillary.setIncludedInFare(request.getIncludedInFare());

        FlightCabinAncillary saved=flightCabinAncillaryRepository.save(flightCabinAncillary);
        return convertToResponse(saved);
    }

    @Override
    public void delete(Long id) throws Exception {
        FlightCabinAncillary flightCabinAncillary=flightCabinAncillaryRepository.findById(id)
                .orElseThrow(()->new Exception("FlightCabinAncillary non trovato"));
        flightCabinAncillaryRepository.delete(flightCabinAncillary);

    }

    @Override
    public Double calculateAncillaryPrice(List<Long> ancillaryIds) {
        List<FlightCabinAncillary> ancillaries=flightCabinAncillaryRepository.findAllById(ancillaryIds);
        double totalPrice=0;
        for (FlightCabinAncillary ancillary:ancillaries){
            totalPrice+=ancillary.getPrice();
        }
        return totalPrice;
    }

    private FlightCabinAncillaryResponse convertToResponse(FlightCabinAncillary ancillary){
        List<InsuranceCoverage> coverages=insuranceCoverageRepository.findByAncillaryId(ancillary.getId());
        List<InsuranceCoverageResponse> coverageResponses=coverages.stream()
                .map(InsuranceCoverageMapper::toDTO).toList();
       return FlightCabinAncillaryMapper.toDTO(ancillary,coverageResponses);
    }
}
