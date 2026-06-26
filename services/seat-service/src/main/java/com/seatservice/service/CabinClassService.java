package com.seatservice.service;

import com.enums.CabinClassType;
import com.payload.request.CabinClassRequest;
import com.payload.response.CabinClassResponse;

import java.util.List;

public interface CabinClassService {

    CabinClassResponse createCabinClass(CabinClassRequest request) throws Exception;
    CabinClassResponse getCabinClassById(Long id) throws Exception;
    List<CabinClassResponse> getCabinClassesByAircraftId(Long aircraftId);
    CabinClassResponse getByAircraftIdAndName(Long aircraft, CabinClassType name);
    CabinClassResponse updateCabinClass(Long id, CabinClassRequest request) throws Exception;
    void deleteCabinClass(Long id) throws Exception;

}
