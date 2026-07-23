package com.payload.response;

import com.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {

    private Long id;

    private String ticketNumber;
    private TicketStatus status;
    private LocalDateTime issuedAt;

    //booking details
    private Long bookingId;
    private String bookingReference;

    //passenger details
    private Long passengerId;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;

    //pyment details
    private Long paymentId;
    private Double paymentAmount;


}
