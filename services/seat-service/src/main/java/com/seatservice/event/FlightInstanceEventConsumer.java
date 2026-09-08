package com.seatservice.event;

import com.enums.SeatAvailabilityStatus;
import com.enums.SeatType;
import com.event.FlightInstanceCreatedEvent;
import com.seatservice.model.CabinClass;
import com.seatservice.model.FlightInstanceCabin;
import com.seatservice.model.Seat;
import com.seatservice.model.SeatInstance;
import com.seatservice.repository.CabinClassRepository;
import com.seatservice.repository.FlightInstanceCabinRepository;
import com.seatservice.repository.SeatInstanceRepository;
import com.seatservice.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightInstanceEventConsumer {

    private final CabinClassRepository cabinClassRepository;
    private final SeatRepository seatRepository;
    private final FlightInstanceCabinRepository flightInstanceCabinRepository;
    private final SeatInstanceRepository seatInstanceRepository;

    @KafkaListener(topics = "flight-instance-created",groupId = "seat-service-group")
    @Transactional
    public void handleFlightInstanceCreated(FlightInstanceCreatedEvent event){

        List<CabinClass> cabinClasses=cabinClassRepository.findByAircraftId(event.getAircraftId());

        int totalSeatInstances=0;

        for (CabinClass cabinClass:cabinClasses){
            List<Seat> seats=cabinClass.getSeatMap() !=null
                    ? seatRepository.findBySeatMapId(cabinClass.getSeatMap().getId())
                    : List.of();

            //for each cabin calss creao flightinstance cabin
            FlightInstanceCabin fic= FlightInstanceCabin.builder()
                    .flightInstanceId(event.getFlightInstanceId())
                    .cabinClass(cabinClass)
                    .totalSeats(seats.size())
                    .bookedSeats(0)
                    .build();

            FlightInstanceCabin savedFic=flightInstanceCabinRepository.save(fic);

            //seats instances for each
            List<SeatInstance> seatInstances=seats.stream().map(
                    seat ->SeatInstance.builder()
                            .flightId(event.getFlightId())
                            .flightInstanceId(event.getFlightInstanceId())
                            .flightInstanceCabin(savedFic)
                            .seat(seat)
                            .status(SeatAvailabilityStatus.AVAILABLE)
                            .isBooked(false)
                            .isAvailable(true)
                            .premiumSuperCharge(getPremiumSuperCharge(
                                    seat.getSeatType(),
                                    1000.0, 500.0))
                            .build()
            ).toList();
            seatInstanceRepository.saveAll(seatInstances);
            totalSeatInstances+=seatInstances.size();


        }
    }




    private Double getPremiumSuperCharge(SeatType seatType,
                                         Double windowsSuperCharge,
                                         Double aisleSuperCharge){
        if (seatType == null) return 0.0;

        return switch (seatType) {
            case AISLE -> aisleSuperCharge;
            case WINDOWS -> windowsSuperCharge;
            default -> 0.0;
        };
    }
}
