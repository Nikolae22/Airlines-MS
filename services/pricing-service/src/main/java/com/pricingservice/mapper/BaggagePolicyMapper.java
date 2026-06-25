package com.pricingservice.mapper;

import com.payload.request.BaggagePolicyRequest;
import com.payload.response.BaggagePolicyResponse;
import com.pricingservice.model.BaggagePolicy;
import com.pricingservice.model.Fare;

public class BaggagePolicyMapper {

    public static BaggagePolicy toEntity(BaggagePolicyRequest request, Fare fare){
        if (request == null) return null;
        return BaggagePolicy.builder()
                .fare(fare)
                .name(request.getName())
                .description(request.getDescription())
                .cabinBaggageMaxWeight(request.getCabinBaggageMaxWeight())
                .cabinBaggagePieces(request.getCabinBaggagePieces() != null ? request.getCabinBaggagePieces() : 1)
                .cabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece()) // Corretto refuso Price -> Piece
                .cabinBaggageMaxDimension(request.getCabinBaggageMaxDimension())
                .checkInBaggageMaxWeight(request.getCheckInBaggageMaxWeight())
                .checkInBaggagePieces(request.getCheckInBaggagePieces() != null ? request.getCheckInBaggagePieces() : 1)
                .checkInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPieces())
                .freeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance() != null ? request.getFreeCheckedBagsAllowance() : 1)
                .priorityBaggage(request.getPriorityBaggage() != null ? request.getPriorityBaggage() : false)
                .extraBaggageAllowance(request.getExtraBaggageAllowance() != null ? request.getExtraBaggageAllowance() : false)
                .build();
    }

    public static BaggagePolicyResponse toDTO(BaggagePolicy policy){
        if (policy == null) return null;
        return BaggagePolicyResponse.builder()
                .id(policy.getId())
                .name(policy.getName())
                .description(policy.getDescription())
                .cabinBaggageMaxWeight(policy.getCabinBaggageMaxWeight())
                .cabinBaggagePieces(policy.getCabinBaggagePieces())
                .cabinBaggageWeightPerPieces(policy.getCabinBaggageWeightPerPiece())
                .cabinBaggageMaxDimension(policy.getCabinBaggageMaxDimension())
                // Check-in
                .checkInBaggageMaxWeight(policy.getCheckInBaggageMaxWeight())
                .checkInBaggagePieces(policy.getCheckInBaggagePieces())
                .checkInBaggageWeightPerPieces(policy.getCheckInBaggageWeightPerPiece())
                // Extra
                .freeCheckedBagsAllowance(policy.getFreeCheckedBagsAllowance())
                .priorityBaggage(policy.getPriorityBaggage())
                .extraBaggageAllowance(policy.getExtraBaggageAllowance())
                // Audit & Relazioni
                .airlineId(policy.getAirlineId())
                .fareId(policy.getFare() != null ? policy.getFare().getId() : null)
                .createdAt(policy.getCreatedAt())
                .updatedAt(policy.getUpdatedAt())
                .build();
    }

    public static void updateEntity(BaggagePolicyRequest request, BaggagePolicy existing){
        if (request == null || existing == null) return;

        // Stringhe e Oggetti complessi
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getCabinBaggageMaxWeight() != null) existing.setCabinBaggageMaxWeight(request.getCabinBaggageMaxWeight());
        if (request.getCabinBaggageMaxDimension() != null) existing.setCabinBaggageMaxDimension(request.getCabinBaggageMaxDimension());
        if (request.getCheckInBaggageMaxWeight() != null) existing.setCheckInBaggageMaxWeight(request.getCheckInBaggageMaxWeight());
        if (request.getCabinBaggageWeightPerPiece() != null) existing.setCabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece());
        if (request.getCheckInBaggageWeightPerPieces() != null) existing.setCheckInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPieces());

        // Campi con valori di default (se arrivano null dalla request, applichiamo il fallback di sicurezza)
        existing.setCabinBaggagePieces(request.getCabinBaggagePieces() != null ? request.getCabinBaggagePieces() : 1);
        existing.setCheckInBaggagePieces(request.getCheckInBaggagePieces() != null ? request.getCheckInBaggagePieces() : 1);
        existing.setFreeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance() != null ? request.getFreeCheckedBagsAllowance() : 1);
        existing.setPriorityBaggage(request.getPriorityBaggage() != null ? request.getPriorityBaggage() : false);
        existing.setExtraBaggageAllowance(request.getExtraBaggageAllowance() != null ? request.getExtraBaggageAllowance() : false);
    }
}
