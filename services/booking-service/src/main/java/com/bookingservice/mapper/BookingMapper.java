package com.bookingservice.mapper;

import com.bookingservice.model.Booking;
import com.bookingservice.model.Passenger;
import com.enums.BookingStatus;
import com.payload.dto.PaymentDTO;
import com.payload.request.BookingRequest;
import com.payload.response.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class BookingMapper {

    public static Booking toEntity(BookingRequest request, Long userId,
                                   Set<Passenger> passengers, String bookingReference) {
        return Booking.builder()
                .bookingReference(bookingReference)
                .userId(userId)
                .flightId(request.getFlightId())
                .flightInstanceId(request.getFlightInstanceId())
                .fareId(request.getFareId())
                .contactInfo(request.getContactInfo())
                .passengers(passengers)
                .cabinClass(request.getCabinClass())
                .ancillaryIds(request.getAncillaryIds())
                .mealIds(request.getMealIds())
                .status(BookingStatus.PENDING)
                .build();
    }


    public static void updateEntityFromRequest(
            BookingRequest request,Booking booking, Set<Passenger> passengers){
        booking.setFlightInstanceId(request.getFlightInstanceId());
        booking.setFlightId(request.getFlightId());
        booking.setFareId(request.getFareId());
        booking.setPassengers(passengers);
        booking.setLastModified(Instant.now());
    }

    public static BookingResponse toDTO(Booking booking,
                                        PaymentDTO paymentDTO,
                                        FareResponse fareResponse,
                                        FlightResponse flightResponse,
                                        FlightInstanceResponse flightInstanceResponse,
                                        List<FlightCabinAncillaryResponse> ancillaries,
                                        List<FlightMealResponse> meals,
                                        List<SeatInstanceResponse> seats){


        List<PassengerResponse> passengerResponses=booking.getPassengers() !=null
                ? booking.getPassengers().stream()
                  .map(PassengerMapper::toDTO)
                  .toList() : null;

        List<TicketResponse> ticketResponses=booking.getTickets() !=null ?
                booking.getTickets().stream()
                        .map(TicketMapper::toDTO)
                        .toList() : null;

        return BookingResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .userId(booking.getUserId())
                //flight details
                .flightId(booking.getFlightInstanceId())
                .flightNumber(flightResponse !=null ? flightResponse.getFlightNumber() : null)
                .flightName(flightResponse !=null  && flightResponse.getArrivalAirport() !=null &&  flightResponse.getDepartureAirport() !=null
                            ? flightResponse.getDepartureAirport().getCity().getName() + " - "+flightResponse.getArrivalAirport().getCity().getName() : null)
                .departureTime(flightInstanceResponse !=null ? flightInstanceResponse.getDepartureDateTime() : null)
                .arrivalTime(flightInstanceResponse !=null ? flightInstanceResponse.getArrivalDateTime() : null)
                .flightDuration(flightInstanceResponse !=null ? flightInstanceResponse.getFormatterDuration() : null)
                //aiport details
                .departureAirport(flightResponse !=null && flightResponse.getDepartureAirport() !=null ? flightResponse.getDepartureAirport().getDetailedNmae() : null )
                .arrivalAirport(flightResponse !=null && flightResponse.getArrivalAirport() !=null ? flightResponse.getArrivalAirport().getName() : null)
                .status(booking.getStatus())
                .bookingDate(booking.getBookingDate())
                .lastModified(booking.getLastModified())
                .passengers(passengerResponses)
                .tickets(ticketResponses)
                .totalPassengers(booking.getPassengers() !=null ? booking.getPassengers().size() : 0)
                .ancillaries(ancillaries)
                .meals(meals)
                .seatInstances(seats)
                .paymentStatus(paymentDTO !=null ? paymentDTO.getStatus() : null)
                // fare dtails
                .fareName(fareResponse !=null ? fareResponse.getName() :  null)
                .fareBaseFare(fareResponse !=null ? fareResponse.getBaseFare() : null)
                .fareTexesAndFees(fareResponse !=null ? fareResponse.getTexesAndFees() : null)
                .fareAirlineFees(fareResponse !=null ? fareResponse.getAirlineFees() : null)
                .totalAmount(fareResponse !=null ? fareResponse.getTotalPrice() : null)
                .contactInfo(booking.getContactInfo())
                .build();
    }
}
