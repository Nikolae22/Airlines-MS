package com.flightopsservice.service.impl;

import com.flightopsservice.mapper.FlightInstanceMapper;
import com.flightopsservice.model.Flight;
import com.flightopsservice.model.FlightInstance;
import com.flightopsservice.repository.FlightInstanceRepository;
import com.flightopsservice.repository.FlightRepository;
import com.flightopsservice.service.FlightInstanceService;
import com.payload.request.FlightInstanceRequest;
import com.payload.response.AircraftResponse;
import com.payload.response.AirlineResponse;
import com.payload.response.AirportResponse;
import com.payload.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;

    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request) throws Exception {

        // todo watch arlineID
        Flight flight=flightRepository.findById(request.getFlightId())
                .orElseThrow(()->new Exception("Flight not found"));

        //dummy aircraft
        // todo service comunication
        AircraftResponse aircraft=AircraftResponse.builder()
                .id(1L)
                .totalSeats(90)
                .build();

        FlightInstance flightInstance= FlightInstanceMapper.toEntity(request,flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance saved=flightInstanceRepository.save(flightInstance);

        // todo create seat instaces

        return convertToFlightInstanceResponse(saved);
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        FlightInstance flightInstance=flightInstanceRepository.findById(id)
                .orElseThrow(()->new Exception("Flight instance not found"));
        return convertToFlightInstanceResponse(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate,
                                                       Pageable pageable) {
        // todo watch arlineId
        LocalDateTime start=onDate!=null ? onDate.atStartOfDay():null;
        LocalDateTime end = onDate !=null ?onDate.plusDays(1).atStartOfDay():null;
        return flightInstanceRepository.findByAirlineId(airlineId,
                        departureAirportId,arrivalAirportId,flightId,
                        start,end,pageable)
                .map(this::convertToFlightInstanceResponse);
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {
        FlightInstance flightInstance=flightInstanceRepository.findById(id)
                .orElseThrow(()->new Exception("Flight instance not found"));
        FlightInstanceMapper.updateEntity(request,flightInstance);
        return convertToFlightInstanceResponse(flightInstanceRepository.save(flightInstance));
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance flightInstance=flightInstanceRepository.findById(id)
                .orElseThrow(()->new Exception("Flight instance not found"));
        flightInstanceRepository.delete(flightInstance);
    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance){
        // todo service to serviceCommunication
        AirlineResponse airline=AirlineResponse.builder()
                .id(flightInstance.getAirlineId())
                .build();
        AirportResponse departureAirport=AirportResponse
                .builder().id(flightInstance.getDepartureAirportId())
                .build();
        AirportResponse arriveAirport=AirportResponse
                .builder().id(flightInstance.getArrivalAirportId())
                .build();
        AircraftResponse aircraft=AircraftResponse.builder()
                .id(flightInstance.getFlight().getAircraftId())
                .build();

        return FlightInstanceMapper.toDTO(
                flightInstance,aircraft,airline,departureAirport,arriveAirport
        );
    }
}
