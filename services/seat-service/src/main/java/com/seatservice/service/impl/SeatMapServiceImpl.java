package com.seatservice.service.impl;

import com.payload.request.SeatMapRequest;
import com.payload.response.AirlineResponse;
import com.payload.response.SeatMapResponse;
import com.seatservice.client.AirlineClient;
import com.seatservice.mapper.SeatMapMapper;
import com.seatservice.model.CabinClass;
import com.seatservice.model.SeatMap;
import com.seatservice.repository.CabinClassRepository;
import com.seatservice.repository.SeatMapRepository;
import com.seatservice.service.SeatMapService;
import com.seatservice.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatMapServiceImpl implements SeatMapService {


    private final SeatMapRepository seatMapRepository;
    private final CabinClassRepository cabinClassRepository;
    private final SeatService seatService;
    private final AirlineClient airlineClient;

    @Override
    public SeatMapResponse createSeatMap(Long userId, SeatMapRequest request) throws Exception {
        CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                .orElseThrow(() -> new Exception("cabin calss not found with this is"));
        AirlineResponse airlineResponse=airlineClient.getAirlineByOwner(userId);
        if (seatMapRepository.existsByAirlineIdAndCabinClassIdAndName(
                airlineResponse.getId(), request.getCabinClassId(), request.getName()
        )) {
            throw new Exception("Cabin class already exists with given name");
        }
        SeatMap seatMap = SeatMapMapper.toEntity(request, cabinClass);
        seatMap.setAirlineId(airlineResponse.getId());
        SeatMap saved = seatMapRepository.save(seatMap);
        seatService.generateSeat(saved.getId());
        return SeatMapMapper.toDTO(saved);
    }

    @Override
    public SeatMapResponse getSeatMapById(Long id) throws Exception {
        SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow(() -> new Exception("Seat map not foudn with id"));
        return SeatMapMapper.toDTO(seatMap);
    }

    @Override
    public SeatMapResponse getSeatMapByCabinClass(Long cabinId) {
        SeatMap seatMap = seatMapRepository.findByCabinClassId(cabinId);
        return SeatMapMapper.toDTO(seatMap);
    }

    @Override
    public SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) throws Exception {
        SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow(() -> new Exception("Seat map not foudn with id"));
        SeatMapMapper.updateEntity(request, seatMap);
        SeatMap saved = seatMapRepository.save(seatMap);
        return SeatMapMapper.toDTO(saved);
    }

    @Override
    public void deleteSeatMap(Long id) throws Exception {
        SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow(() -> new Exception("Seat map not foudn with id"));

        seatMapRepository.delete(seatMap);
    }

}
