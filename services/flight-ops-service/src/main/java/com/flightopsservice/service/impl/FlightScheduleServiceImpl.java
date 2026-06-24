package com.flightopsservice.service.impl;

import com.enums.FlightStatus;
import com.flightopsservice.mapper.FlightInstanceMapper;
import com.flightopsservice.mapper.FlightScheduleMapper;
import com.flightopsservice.model.Flight;
import com.flightopsservice.model.FlightSchedule;
import com.flightopsservice.repository.FlightRepository;
import com.flightopsservice.repository.FlightScheduleRepository;
import com.flightopsservice.service.FlightInstanceService;
import com.flightopsservice.service.FlightScheduleService;
import com.payload.request.FlightInstanceRequest;
import com.payload.request.FlightScheduleRequest;
import com.payload.response.AirportResponse;
import com.payload.response.FlightScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId,
                                                       FlightScheduleRequest request) throws Exception {

        //todo watch for airlineid
        Flight flight =flightRepository.findById(request.getFlightId())
                .orElseThrow(()->new Exception("FLight not found with given id"));
        if (request.getEndDate().isBefore(request.getStartDate())){
            throw new Exception("end date is before start date");
        }

        FlightSchedule flightSchedule= FlightScheduleMapper.toEntity(
                request,flight);
        FlightSchedule saved = flightScheduleRepository.save(flightSchedule);

        //create flight instance for saved scheduled
        //start end 11/03/2026 10/04/2026
        // mon tue ,fri ,sat
        List<DayOfWeek> operatingDays=saved.getOperatingDays();
        LocalDate startDate=saved.getStartDate();
        LocalDate endDate=saved.getEndDate();
        FlightInstanceRequest flightInstanceRequest=FlightInstanceRequest
                .builder()
                .scheduleId(saved.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();

        //for loop per aumentare i giorni fino al end date
        for (LocalDate date=startDate;
             !date.isAfter(endDate);
             date=date.plusDays(1)){
            //qeusto if mi serve per solo i gironi che mi servono
            if (operatingDays.contains(date.getDayOfWeek())) {
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date,saved.getDepartureTime())
                );
                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date,saved.getArrivalTime())
                );
                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);
            }
        }
        return convertToFlightScheduleResponse(saved);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
        FlightSchedule flightSchedule=flightScheduleRepository.findById(id)
                .orElseThrow(()->new Exception("Fligh scheule not found with id"));
        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId) {
        //todo watch ariline id
        List<FlightSchedule> schedules=flightScheduleRepository.findByFlightAirlineId(userId);
        return schedules.stream()
                .map(this::convertToFlightScheduleResponse)
                .toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception {
        FlightSchedule flightSchedule=flightScheduleRepository.findById(id)
                .orElseThrow(()->new Exception("Flight schedule not found with id"));
        FlightScheduleMapper.updateEntity(request,flightSchedule);
        FlightSchedule saved = flightScheduleRepository.save(flightSchedule);
        return convertToFlightScheduleResponse(saved);
    }

    @Override
    public void deleteFlightSchedule(Long id) throws Exception {
        FlightSchedule flightSchedule=flightScheduleRepository.findById(id)
                .orElseThrow(()->new Exception("Fligh scheule not found with id"));
        flightScheduleRepository.delete(flightSchedule);
    }

    private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule){
        // todo service to service comuncaiton
        AirportResponse departureAirport=AirportResponse
                .builder()
                .id(flightSchedule.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport=AirportResponse
                .builder()
                .id(flightSchedule.getArrivalAirportId())
                .build();
        return FlightScheduleMapper.toDTO(
                flightSchedule,arrivalAirport,departureAirport
        );
    }
}
