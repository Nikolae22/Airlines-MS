package com.seatservice.service.impl;

import com.enums.SeatAvailabilityStatus;
import com.enums.SeatType;
import com.payload.request.FlightInstanceCabinRequest;
import com.payload.response.FlightInstanceCabinResponse;
import com.seatservice.mapper.FlightInstanceCabinMapper;
import com.seatservice.model.CabinClass;
import com.seatservice.model.FlightInstanceCabin;
import com.seatservice.model.SeatInstance;
import com.seatservice.model.SeatMap;
import com.seatservice.repository.CabinClassRepository;
import com.seatservice.repository.FlightInstanceCabinRepository;
import com.seatservice.repository.SeatInstanceRepository;
import com.seatservice.repository.SeatMapRepository;
import com.seatservice.service.FlightInstanceCabinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightInstanceCabinServiceImpl implements FlightInstanceCabinService {

    private final FlightInstanceCabinRepository flightInstanceCabinRepository;
    private final CabinClassRepository cabinClassRepository;
    private final SeatMapRepository seatMapRepository;
    private final SeatInstanceRepository seatInstanceRepository;
    @Override
    public FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request) throws Exception {
        CabinClass cabinClass=cabinClassRepository.findById(request.getCabinClassId())
                .orElseThrow(()->new Exception("Cabin class not found"));
        SeatMap seatMap=seatMapRepository.findByCabinClassId(cabinClass.getId());
        if (seatMap == null){
            throw new Exception("Seat map not found");
        }

        if (seatMap.getSeats() == null || seatMap.getSeats().isEmpty()){
            throw  new Exception("No seat found in seat map");
        }
        int totalSeats=seatMap.getSeats().size();
        FlightInstanceCabin cabin=FlightInstanceCabin.builder()
                .flightInstanceId(request.getFlightInstanceId())
                .cabinClass(cabinClass)
                .totalSeats(totalSeats)
                .bookedSeats(0)
                .build();
        FlightInstanceCabin saved=flightInstanceCabinRepository.save(cabin);

        // todo generate seat instances
        List<SeatInstance> seatInstances=seatMap.getSeats()
                .stream()
                .map(
                        seat -> {
                            Double premiumSuperCharge=getPremiumSuperCharge(
                                    seat.getSeatType(),
                                    1000.0,
                                    500.0);
                            SeatInstance seatInstance=SeatInstance.builder()
                                    .flightId(request.getFlightId())
                                    .status(SeatAvailabilityStatus.AVAILABLE)
                                    .flightInstanceId(request.getFlightInstanceId())
                                    .flightInstanceCabin(saved)
                                    .seat(seat)
                                    .isAvailable(true)
                                    .isBooked(false)
                                    .premiumSuperCharge(premiumSuperCharge)
                                    .build();
                            return seatInstance;
                        }
                ).toList();

        seatInstanceRepository.saveAll(seatInstances);
        saved.setSeats(seatInstances);
        return FlightInstanceCabinMapper.toDTO(saved);
    }

    @Override
    public FlightInstanceCabinResponse getFlightInstanceCabinById(Long id) throws Exception {
        FlightInstanceCabin fic=flightInstanceCabinRepository.findById(id)
                .orElseThrow(()->new Exception("Flight instance not found"));
        return FlightInstanceCabinMapper.toDTO(fic);
    }

    @Override
    public Page<FlightInstanceCabinResponse> getByFlightInstanceId(Long id, Pageable pageable) {
        return flightInstanceCabinRepository.findByFlightInstanceId(id,pageable)
                .map(FlightInstanceCabinMapper::toDTO);
    }

    @Override
    public FlightInstanceCabinResponse getByFlightInstanceIdAndCabinClassId(Long flightInstanceId, Long cabinClassId) {
        FlightInstanceCabin cabin=flightInstanceCabinRepository.findByFlightInstanceIdAndCabinClassId(
                flightInstanceId,cabinClassId
        );
        return FlightInstanceCabinMapper.toDTO(cabin);
    }

    @Override
    public FlightInstanceCabinResponse updateFlightInstanceCabin(Long id, FlightInstanceCabinRequest request) throws Exception {
        FlightInstanceCabin fic=flightInstanceCabinRepository.findById(id)
                .orElseThrow(()->new Exception("Flight instance not found"));
        if (request.getCabinClassId() !=null){
            CabinClass cabinClass=cabinClassRepository.findById(request.getCabinClassId())
                    .orElseThrow(()->new Exception("Cabin calass nto found"));
            fic.setCabinClass(cabinClass);
        }
        FlightInstanceCabin saved = flightInstanceCabinRepository.save(fic);
        return FlightInstanceCabinMapper.toDTO(saved);
    }

    @Override
    public void deleteFlightInstanceCabin(Long id) throws Exception {
        FlightInstanceCabin fic=flightInstanceCabinRepository.findById(id)
                .orElseThrow(()->new Exception("Flight instance not found"));
        flightInstanceCabinRepository.delete(fic);
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
