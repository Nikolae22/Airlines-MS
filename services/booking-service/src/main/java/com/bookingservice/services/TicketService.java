package com.bookingservice.services;

import com.bookingservice.model.Booking;
import com.bookingservice.model.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> generateTicketsForBooking(Booking booking);
}
