package com.flightopsservice.mapper;

import com.enums.FlightStatus;
import com.flightopsservice.model.Flight;
import com.flightopsservice.model.FlightInstance;
import com.payload.request.FlightInstanceRequest;
import com.payload.response.AircraftResponse;
import com.payload.response.AirlineResponse;
import com.payload.response.AirportResponse;
import com.payload.response.FlightInstanceResponse;

public class FlightInstanceMapper {

    public static FlightInstance toEntity(FlightInstanceRequest request,
                                          Flight flight) {
        if (flight == null) return null;
        return FlightInstance.builder()
                .airlineId(flight.getAirlineId())
                .flight(flight)
                .scheduleId(request.getScheduleId())
                .departureAirportId(request.getDepartureAirportId() != null
                        ? request.getDepartureAirportId() : null)
                .arrivalAirportId(request.getArrivalAirportId() != null
                        ? request.getArrivalAirportId() : null)
                .arrivalDateTime(request.getArrivalDateTime())
                .status(FlightStatus.SCHEDULED)
                .minAdvanceBookingDays(request.getMinAdvancedBookingDays())
                .maxAdvanceBookingDays(request.getMaxAdvancedBookingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightInstanceResponse toDTO(FlightInstance fi,
                                               AircraftResponse aircraftResponse,
                                               AirlineResponse airline,
                                               AirportResponse departureAirport,
                                               AirportResponse arrivalAirport){
        if (fi == null) return null;
        return FlightInstanceResponse.builder()
                .id(fi.getId())
                .flightId(fi.getFlight() !=null ? fi.getFlight().getId() : null)
                .flightNumber(fi.getFlight() !=null ? new StringBuilder(fi.getFlight().getFlightNumber()) : null)
                .aircraftId(fi.getFlight().getAircraftId())
                .aircraftModal(aircraftResponse.getModel())
                .aircraftCode(aircraftResponse.getCode())
                .airlineId(fi.getAirlineId())
                .airlineName(airline.getName())
                .airlineLogo(airline.getLogoUrl())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(fi.getDepartureDateTime())
                .arrivalDateTime(fi.getArrivalDateTime())
                .formatterDuration(fi.getFormatedDuration())
                .totalSetas(fi.getTotalSeats())
                .availableSeats(fi.getAvailableSeats())
                .status(fi.getStatus())
                .minAdvanceBookingDays(fi.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(fi.getMaxAdvanceBookingDays())
                .isActive(fi.getIsActive())
//                .terminal(fi.getTerminal())
//                .gate(fi.getGate())
//                .version(fi.getVersion())
                .build();
    }

    public static void updateEntity(FlightInstanceRequest request,FlightInstance existing){
        if (request == null || existing == null)return;
        if (request.getDepartureAirportId() !=null) existing.setDepartureAirportId(request.getDepartureAirportId());
        if (request.getArrivalAirportId() !=null) existing.setArrivalAirportId(request.getArrivalAirportId());
        if (request.getDepartureDateTime() !=null) existing.setDepartureDateTime(request.getDepartureDateTime());
        if (request.getArrivalDateTime() !=null) existing.setArrivalDateTime(request.getArrivalDateTime());
        if (request.getAvailableSeats() !=null) existing.setAvailableSeats(request.getAvailableSeats());
        if (request.getMinAdvancedBookingDays() !=null) existing.setMinAdvanceBookingDays(request.getMinAdvancedBookingDays());
        if (request.getMaxAdvancedBookingDays() !=null) existing.setMaxAdvanceBookingDays(request.getMaxAdvancedBookingDays());
        if (request.getIsActive() !=null) existing.setIsActive(request.getIsActive());
    }
}
