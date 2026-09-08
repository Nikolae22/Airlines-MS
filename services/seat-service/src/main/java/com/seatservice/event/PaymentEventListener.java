package com.seatservice.event;

import com.enums.SeatAvailabilityStatus;
import com.event.PaymentCompletedEvent;
import com.payload.response.BookingResponse;
import com.payload.response.SeatInstanceResponse;
import com.seatservice.client.BookingClient;
import com.seatservice.service.SeatInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingClient bookingClient;
    private final SeatInstanceService seatInstanceService;

    @KafkaListener(topics = "payment-completed",groupId = "seat-service-group")
    public void handleBookingConfirmed(PaymentCompletedEvent event){

        BookingResponse bookingResponse=bookingClient.getBookingById(event.getBookingId());

        List<SeatInstanceResponse> seatInstances=bookingResponse.getSeatInstances();

        for (SeatInstanceResponse seatInstanceResponse:seatInstances){
            seatInstanceService.updateSeatInstanceStatus(seatInstanceResponse.getId(),
                    SeatAvailabilityStatus.BOOKED);
        }

    }


}
