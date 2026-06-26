package com.seatservice.service;

import com.payload.request.SeatMapRequest;
import com.payload.response.SeatMapResponse;

public interface SeatMapService {

    SeatMapResponse createSeatMap(Long airlineId, SeatMapRequest request) throws Exception;
    SeatMapResponse getSeatMapById(Long id) throws Exception;
    SeatMapResponse getSeatMapByCabinClass(Long cabinId);
    SeatMapResponse updateSeatMap(Long id ,SeatMapRequest request) throws Exception;
    void deleteSeatMap(Long id) throws Exception;
}
