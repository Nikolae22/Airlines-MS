package com.bookingservice.services.imp;

import com.bookingservice.model.Booking;
import com.bookingservice.model.Passenger;
import com.bookingservice.model.Ticket;
import com.bookingservice.repository.TicketRepository;
import com.bookingservice.services.TicketService;
import com.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> generateTicketsForBooking(Booking booking) {
        List<Ticket> tickets=new ArrayList<Ticket>();

        for (Passenger passenger:booking.getPassengers()){
            String ticketNumber=generateUniqueTicketNumber();

            Ticket ticket=Ticket.builder()
                    .ticketNumber(ticketNumber)
                    .status(TicketStatus.BOOKED)
                    .issueAt(LocalDateTime.now())
                    .booking(booking)
                    .passenger(passenger)
                    .build();
            Ticket saved=ticketRepository.save(ticket);
            tickets.add(saved);
        }
        return tickets;
    }


    private String generateUniqueTicketNumber(){
        String ticketNumber;
        do {
            String datePart=LocalDateTime.now().toString()
                    .substring(0,10);
            String randomPart= UUID.randomUUID().toString().substring(0,8);
            ticketNumber=String.format("TKT-%s-%s",datePart,randomPart);
        }while (ticketRepository.existsByTicketNumber(ticketNumber));
        return ticketNumber;
    }
}
