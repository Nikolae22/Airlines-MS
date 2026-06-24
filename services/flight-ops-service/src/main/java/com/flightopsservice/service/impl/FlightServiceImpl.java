package com.flightopsservice.service.impl;

import com.enums.FlightStatus;
import com.flightopsservice.repository.FlightRepository;
import com.flightopsservice.mapper.FlightMapper;
import com.flightopsservice.model.Flight;
import com.flightopsservice.service.FlightService;
import com.payload.request.FlightRequest;
import com.payload.response.AircraftResponse;
import com.payload.response.AirlineResponse;
import com.payload.response.AirportResponse;
import com.payload.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest request) throws Exception {

        // todo watch airlineId
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())){
            throw new Exception("Flight with id already exists");
        }
        Flight flight=FlightMapper.toEntity(request);
        flight.setAirlineId(airlineId);
        Flight saved = flightRepository.save(flight);
        return convertToFlightResponse(saved);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long airlineId,
                                                    Long departureAirportId,
                                                    Long arrivalAirportId,
                                                    Pageable pageable) {
        // todo watchAirlineId
        return flightRepository.findByAirlineId(airlineId,
                departureAirportId,arrivalAirportId,pageable)
                .map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight=flightRepository.findById(id)
                .orElseThrow(()->new Exception("Flight not found with this id"));
        return convertToFlightResponse(flight);
    }


    //f-450       f-450
    //f-451       f-450 depId

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest request) throws Exception {
        Flight flight=flightRepository.findById(id)
                .orElseThrow(()->new Exception("Flight not found with this id"));
        if (request.getFlightNumber() !=null &&
        flightRepository.existsByFlightNumberAndIdNot(request.getFlightNumber(),id)){
            throw new Exception("Flight with id alrady exists");
        }
        FlightMapper.updateEntity(request,flight);
        Flight updated=FlightMapper.toEntity(request);
        return convertToFlightResponse(updated);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) throws Exception {
        Flight flight=flightRepository.findById(id)
                .orElseThrow(()->new Exception("Flight not found with this id"));
        flight.setStatus(status);
        Flight saved = flightRepository.save(flight);
        return convertToFlightResponse(saved);
    }

    @Override
    public void deleteFlight(Long airlineId,Long id) throws Exception {
        //todo watchAirlineId
        Flight flight=flightRepository.findByAirlineIdAndId(airlineId,id)
                .orElseThrow(()->new Exception("Flight not found with this id"));
        flightRepository.delete(flight);
    }


    public FlightResponse convertToFlightResponse(Flight flight){
        // todo service to serviceCommunication
        AircraftResponse aircraft=AircraftResponse.builder()
                .id(flight.getAircraftId()).build();
        AirlineResponse airline=AirlineResponse.builder().id(flight.getAirlineId()).build();
        AirportResponse departureAirport=AirportResponse.builder()
                .id(flight.getDepartureAirportId()).build();
        AirportResponse arrivalAirport=AirportResponse
                .builder().id(flight.getArrivalAirportId()).build();
        return FlightMapper.toDTO(flight,aircraft,
                airline,departureAirport,arrivalAirport);
    }
}
