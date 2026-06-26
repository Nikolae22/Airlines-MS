package com.seatservice.service;

import com.payload.request.SeatRequest;
import com.payload.response.SeatResponse;

import java.util.List;

public interface SeatService {

    void generateSeat(Long setMapId) throws Exception;
    List<SeatResponse> getAll();
    SeatResponse updateSeats(Long seatId, SeatRequest request);
}
