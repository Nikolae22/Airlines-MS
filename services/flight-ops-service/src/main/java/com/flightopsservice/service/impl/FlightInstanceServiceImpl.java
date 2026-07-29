package com.flightopsservice.service.impl;

import com.flightopsservice.client.AirlineClient;
import com.flightopsservice.client.LocationClient;
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
    private final AirlineClient airlineClient;
    private final LocationClient locationClient;

    @Override
    public FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request) throws Exception {

        //fetch arlineID
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new Exception("Flight not found"));

        //dummy aircraft
        // service comunication
//        AircraftResponse aircraft=AircraftResponse.builder()
//                .id(1L)
//                .totalSeats(90)
//                .build();
        //get aircraft data from airline core service
        AircraftResponse aircraft = airlineClient.getAircraftById(flight.getAircraftId());
        FlightInstance flightInstance = FlightInstanceMapper.toEntity(request, flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance saved = flightInstanceRepository.save(flightInstance);

        // todo create seat instaces
        //publish kafka event seat service consume that and create seta intance

        return convertToFlightInstanceResponse(saved);
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found"));
        return convertToFlightInstanceResponse(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long userId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate,
                                                       Pageable pageable) {
        // fetcj by ownerId
        AirlineResponse airlineResponse = airlineClient.getAirlineByOwner(userId);
        LocalDateTime start = onDate != null ? onDate.atStartOfDay() : null;
        LocalDateTime end = onDate != null ? onDate.plusDays(1).atStartOfDay() : null;
        return flightInstanceRepository.findByAirlineId(
                        airlineResponse.getId(),
                        departureAirportId, arrivalAirportId, flightId,
                        start, end, pageable)
                .map(this::convertToFlightInstanceResponse);
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found"));
        FlightInstanceMapper.updateEntity(request, flightInstance);
        return convertToFlightInstanceResponse(flightInstanceRepository.save(flightInstance));
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found"));
        flightInstanceRepository.delete(flightInstance);
    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance) {
//        AirlineResponse airline=AirlineResponse.builder()
//                .id(flightInstance.getAirlineId())
//                .build();
//        AirportResponse departureAirport=AirportResponse
//                .builder().id(flightInstance.getDepartureAirportId())
//                .build();
//        AirportResponse arriveAirport=AirportResponse
//                .builder().id(flightInstance.getArrivalAirportId())
//                .build();
//        AircraftResponse aircraft=AircraftResponse.builder()
//                .id(flightInstance.getFlight().getAircraftId())
//                .build();
        //  service to serviceCommunication
        AirlineResponse airline = airlineClient.getAirlineById(flightInstance.getAirlineId());
        AirportResponse departureAirport = locationClient.getAirportById(flightInstance.getDepartureAirportId());
        AirportResponse arriveAirport = locationClient.getAirportById(flightInstance.getArrivalAirportId());
        AircraftResponse aircraft = airlineClient.getAircraftById(flightInstance.getFlight().getAircraftId());

        return FlightInstanceMapper.toDTO(
                flightInstance, aircraft, airline, departureAirport, arriveAirport
        );
    }
}
