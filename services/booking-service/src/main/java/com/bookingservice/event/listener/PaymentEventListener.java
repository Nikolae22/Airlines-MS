package com.bookingservice.event.listener;

import com.bookingservice.client.FlightClient;
import com.bookingservice.client.PricingClient;
import com.bookingservice.client.UserClient;
import com.bookingservice.model.Booking;
import com.bookingservice.repository.BookingRepository;
import com.enums.BookingStatus;
import com.event.PaymentCompletedEvent;
import com.event.PaymentFailedEvent;
import com.payload.dto.UserDTO;
import com.payload.response.FareResponse;
import com.payload.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final PricingClient pricingClient;
    private final UserClient userClient;

    @KafkaListener(topics = "payment.completed",groupId = "booking-service-group")
    public void handlePaymentCompleted(PaymentCompletedEvent event){
        //fetch booking
        Booking booking=bookingRepository.findById(event.getBookingId())
                .orElse( null);
        if (booking==null) {return;}
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        FlightInstanceResponse flightInstanceResponse=flightClient.
                getFlightInstanceById(booking.getFlightInstanceId());
        FareResponse fareResponse=pricingClient.getFareById(booking.getFareId());
        UserDTO userDTO=userClient.getUserById(booking.getUserId());

        //publish event for seta service and notifiaction service both consume it

    }

    @KafkaListener(topics = "payment-failed",groupId ="booking-service-group")
    public void handlePaymentFailed(PaymentFailedEvent event){
        //fetch booking
        Booking booking=bookingRepository.findById(event.getBookingId())
                .orElse( null);
        if (booking==null) {return;}

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
