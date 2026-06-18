package com.locationservice.service.impl;

import com.locationservice.mapper.AirportMapper;
import com.locationservice.model.Airport;
import com.locationservice.model.City;
import com.locationservice.payload.request.AirportRequest;
import com.locationservice.payload.response.AirportResponse;
import com.locationservice.repository.AirportRepository;
import com.locationservice.repository.CityRepository;
import com.locationservice.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest airportRequest) throws Exception {

        if (airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()){
            throw new Exception("Airport with IATA code already exists");
        }
        City city=cityRepository.findById(airportRequest.getCityId())
                .orElseThrow(()->new Exception("City not found"));
        Airport airport= AirportMapper.toEntity(airportRequest);
        airport.setCity(city);
        Airport saved = airportRepository.save(airport);
        return AirportMapper.toDTO(saved);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport= airportRepository.findById(id)
                .orElseThrow(()->new Exception("Airport not exists with this id"));
        return AirportMapper.toDTO(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll()
                .stream()
                .map(AirportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport airport= airportRepository.findById(id)
                .orElseThrow(()->new Exception("Airport not exists with this id"));
        if (request.getIataCode() !=null
                && !airport.getIataCode().equals(request.getIataCode())
                && airportRepository.findByIataCode(request.getIataCode()).isPresent()){
            throw new Exception("Airport with IATA code already exists");
        }
        AirportMapper.updateEntity(request,airport);
        Airport saved = airportRepository.save(airport);
        return AirportMapper.toDTO(saved);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport airport= airportRepository.findById(id)
                .orElseThrow(()->new Exception("Airport not exists with this id"));
        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toDTO)
                .collect(Collectors.toList());
    }
}
