package com.ancillaryservice.mapper;

import com.ancillaryservice.model.Ancillary;
import com.payload.request.AncillaryRequest;
import com.payload.response.AncillaryResponse;
import com.payload.response.InsuranceCoverageResponse;

import java.util.List;

public class AncillaryMapper {

    public static Ancillary toEntity(AncillaryRequest ancillaryRequest){
        if (ancillaryRequest ==null) return null;
        return null;
    }

    public static AncillaryResponse toDTO(Ancillary ancillary, List<InsuranceCoverageResponse> coverageResponses){
        if (ancillary ==null) return null;
        return AncillaryResponse.builder()
                .id(ancillary.getId())
                .type(ancillary.getType())
                .subType(ancillary.getSubType())
                .rfisc(ancillary.getRfisc())
                .name(ancillary.getName())
                .description(ancillary.getDescription())
                .metadata(ancillary.getMetadata())
                .coverages(coverageResponses)
                .displayOrder(ancillary.getDisplayOrder())
                .airlineId(ancillary.getAirlineId())
                .build();
    }
}
