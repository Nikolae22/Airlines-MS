package com.bookingservice.repository;

import com.bookingservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
            SELECT t FROM Ticket t
            left join fetch t.booking
            left join fetch t.passenger
            WHERE t.booking.id   = :bookingId
            """)
    List<Ticket> findByBookingIdWithDetails(@Param("bookingId") Long bookingId);

    List<Ticket> findByBookingId(Long bookingId);

    boolean existsByTicketNumber(String ticketNumber);

}
