package com.ancillaryservice.services;

import com.payload.request.AncillaryRequest;
import com.payload.response.AncillaryResponse;

import java.util.List;

public interface AncillaryService {

    AncillaryResponse createAncillary(Long userId, AncillaryRequest request);
    AncillaryResponse getById(Long id) throws Exception;
    List<AncillaryResponse> getByAirlineId(Long userId);
    AncillaryResponse updateAncillary(Long id, AncillaryRequest request) throws Exception;
    void deleteAncillary(Long id) throws Exception;
}
