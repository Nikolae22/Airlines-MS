package com.airlanecoreservice.service.impl;

import com.airlanecoreservice.mapper.AirlineMapper;
import com.airlanecoreservice.model.Airline;
import com.airlanecoreservice.repository.AirlineRepository;
import com.airlanecoreservice.service.AirlineService;
import com.enums.AirlineStatus;
import com.payload.request.AirlineRequest;
import com.payload.response.AirlineDropdownItem;
import com.payload.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) {
        Airline airline= AirlineMapper.toEntity(request,ownerId);
        Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toResponse(saved);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found with ownerId " + ownerId));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new Exception("Airline not found with id " + id));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable)
                .map(AirlineMapper::toResponse);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found with ownerId " + ownerId));
        AirlineMapper.updateEntity(airline,request);
        Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toResponse(saved);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found with ownerId " + ownerId));
       airlineRepository.delete(airline);

    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline not found with id " + airlineId));
        airline.setStatus(status);
        Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toResponse(saved);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropDown() {
        airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a -> AirlineDropdownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .icaoCode(a.getIcaoCode())
                        .logoUrl(a.getLogoUrl())
                        .build()
                ).toList();
        return List.of();
    }
}
