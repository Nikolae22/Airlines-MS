package com.bookingservice.mapper;

import com.bookingservice.model.Ticket;
import com.payload.response.TicketResponse;

public class TicketMapper {

    public static TicketResponse toDTO(Ticket ticket){

        if (ticket == null){
            return null;
        }

        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .status(ticket.getStatus())
                .issuedAt(ticket.getIssueAt())
                .bookingId(ticket.getBooking() !=null ? ticket.getBooking().getId() : null)
                .bookingReference(ticket.getBooking() !=null ? ticket.getBooking().getBookingReference() :null)
                .passengerId(ticket.getPassenger() !=null ? ticket.getPassenger().getId(): null)
                .passengerFirstName(ticket.getPassenger() !=null ? ticket.getPassenger().getFirstName() : null)
                .passengerLastName(ticket.getPassenger() !=null ? ticket.getPassenger().getLastName() : null)
                .passengerEmail(ticket.getPassenger() !=null ? ticket.getPassenger().getEmail() : null)
                .build();
    }
}
