package com.airlanecoreservice.service.impl;

import com.airlanecoreservice.mapper.AircraftMapper;
import com.airlanecoreservice.model.Aircraft;
import com.airlanecoreservice.model.Airline;
import com.airlanecoreservice.repository.AircraftRepository;
import com.airlanecoreservice.repository.AirlineRepository;
import com.airlanecoreservice.service.AircraftService;
import com.payload.request.AircraftRequest;
import com.payload.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
      Airline airline= airlineRepository.findByOwnerId(ownerId)
              .orElseThrow(()->new Exception("airline not exists for this ownerID"));

        Aircraft aircraft= AircraftMapper.toEntity(request,airline);

        if (aircraftRepository.existsByCode(aircraft.getCode())){
            throw new Exception("Code already exists with another aircraft");
        }

        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()){
            throw new Exception("seating capacity cant exceed to total seats");
        }
        return AircraftMapper.toDTO(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new Exception("Aircraft not exists with this id"));
        return AircraftMapper.toDTO(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {
        Airline airline=airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(()->new Exception("This owner dont have airlines"));
        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toDTO)
                .toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id,AircraftRequest request, Long ownerId) throws Exception {
        Airline airline=airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(()->new Exception("This owner dont have airlines"));
        Aircraft aircraft=aircraftRepository.findByIdAndAirlineId(
                id,airline.getId()
        );
        if (aircraft==null){
            throw new Exception("Aircraft not exists with id");
        }

        if (request.getCode() !=null && aircraft.getCode().equals(request.getCode())
            && aircraftRepository.existsByCode(request.getCode())){
            throw new Exception("Code already exists with another aircraft");
        }
        AircraftMapper.updateEntity(aircraft,request);
        return AircraftMapper.toDTO(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline arline=airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(()->new Exception("This owner dont have airline"));
        Aircraft aircraft=aircraftRepository.findByIdAndAirlineId(id,arline.getId());
        if (aircraft ==null){
            throw new Exception("Aircraft not exists with this id");
        }
        aircraftRepository.delete(aircraft);
    }
}
