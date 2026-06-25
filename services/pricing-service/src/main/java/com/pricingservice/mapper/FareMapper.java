package com.pricingservice.mapper;

import com.embeddable.*;
import com.payload.request.FareRequest;
import com.payload.response.FareResponse;
import com.pricingservice.model.Fare;

public class FareMapper {

    public static Fare toEntity(FareRequest request) {
        if (request == null) return null;

        Double calculatedPrice = request.getCurrentPrice();
        if (calculatedPrice == null) {
            calculatedPrice = request.getBaseFare()
                    + request.getTaxesAndFees() +
                    request.getAirlineFees();
        }
        ;

        SeatBenefits seatBenefits = SeatBenefits.builder()
                .extraSeatSpace(bool(request.getExtraSeatSpace()))
                .preferredSeatChoice(bool(request.getPreferredSeatChoice()))
                .advanceSeatSelection(bool(request.getAdvanceSeatSelection()))
                .guaranteedSeatTogether(bool(request.getGuaranteedSeatTogether()))
                .build();

        BoardingBenefits boardingBenefits=BoardingBenefits.builder()
                .priorityBoarding(bool(request.getPriorityBoarding()))
                .priorityChecking(bool(request.getPriorityChecking()))
                .fastTrackSecurity(bool(request.getFastTrackSecurity()))
                .build();

        InFlightBenefits inFlightBenefits=InFlightBenefits.builder()
                .complimentaryMeals(bool(request.getComplimentaryMeals()))
                .premiumMealChoice(bool(request.getPremiumMealChoice()))
                .inFlightInternet(bool(request.getInFlightInternet()))
                .inFlightEntertainment(bool(request.getInFlightEntertainment()))
                .complimentaryBeverage(bool(request.getComplimentaryBeverages()))
                .build();

        FlexibilityBenefits flexibilityBenefits=FlexibilityBenefits.builder()
                .freeDataChange(bool(request.getFreeDataChange()))
                .partialRefund(bool(request.getPartialRefund()))
                .fullRefund(bool(request.getFullRefund()))
                .build();

        PremiumServiceBenefits premiumServiceBenefits=PremiumServiceBenefits.builder()
                .loungeAccess(bool(request.getLoungeAccess()))
                .airportTransfer(bool(request.getAirportTransfer()))
                .build();


        return Fare.builder()
                .name(request.getName())
                .rdbCode(request.getRbdCode())
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .baseFare(request.getBaseFare())
                .taxesAndFees(request.getTaxesAndFees())
                .airlineFees(request.getAirlineFees())
                .currentPrice(calculatedPrice)
                .fareLabel(request.getFareLabel())
                .seatBenefits(seatBenefits)
                .boardingBenefits(boardingBenefits)
                .inFlightBenefits(inFlightBenefits)
                .flexibilityBenefit(flexibilityBenefits)
                .premiumServiceBenefits(premiumServiceBenefits)
                .build();
    }

    public static FareResponse toDTO(Fare fare){
        if (fare == null)return null;
        return FareResponse.builder()
                .id(fare.getId())
                .name(fare.getName())
                .rbdCode(fare.getRdbCode())
                .flightId(fare.getFlightId())
                .cabinClassId(fare.getCabinClassId())
                .cabinClass(fare.getCabinClass())
                .baseFare(fare.getBaseFare())
                .texesAndFees(fare.getTaxesAndFees())
                .airlineFees(fare.getAirlineFees())
                .currentPrice(fare.getCurrentPrice())
                .totalPrice(fare.getTotalPrice())
                .fareLabel(fare.getFareLabel())

                .fareRulesId(fare.getFareRules() !=null ? fare.getFareRules().getId() : null)
                //seats benefits
                .extraSeatSpace(fare.getSeatBenefits() !=null ? fare.getSeatBenefits().getExtraSeatSpace() : false)
                .preferredSeatChoice(fare.getSeatBenefits() !=null ? fare.getSeatBenefits().getPreferredSeatChoice() : false)
                .advanceSeatSelection(fare.getSeatBenefits() !=null ? fare.getSeatBenefits().getAdvanceSeatSelection() : false)
                .guaranteedSeatTogether(fare.getSeatBenefits() !=null ? fare.getSeatBenefits().getGuaranteedSeatTogether() : false)
                //boarding benefits
                .priorityBoarding(fare.getBoardingBenefits() !=null ? fare.getBoardingBenefits().getPriorityBoarding() : false)
                .priorityChecking(fare.getBoardingBenefits() !=null ? fare.getBoardingBenefits().getPriorityChecking() : false)
                .fastTrackSecurity(fare.getBoardingBenefits() !=null ? fare.getBoardingBenefits().getFastTrackSecurity() : false)
                //inflight benefits
                .complimentaryMeals(fare.getInFlightBenefits() !=null ? fare.getInFlightBenefits().getComplimentaryMeals() : false)
                .premiumMealChoice(fare.getInFlightBenefits() !=null ? fare.getInFlightBenefits().getPremiumMealChoice() : false)
                .inFlightInternet(fare.getInFlightBenefits() !=null ? fare.getInFlightBenefits().getInFlightInternet() : false)
                .inFlightEntertainment(fare.getInFlightBenefits() !=null ? fare.getInFlightBenefits().getInFlightEntertainment() : false)
                .complimentaryBeverages(fare.getInFlightBenefits() !=null ? fare.getInFlightBenefits().getComplimentaryBeverage() : false)
                //flexibility benefits
                .freeDataChange(fare.getFlexibilityBenefit() !=null ? fare.getFlexibilityBenefit().getFreeDataChange() : false)
                .partialRefund(fare.getFlexibilityBenefit() !=null ? fare.getFlexibilityBenefit().getPartialRefund() : false)
                .fullRefund(fare.getFlexibilityBenefit() !=null ? fare.getFlexibilityBenefit().getFullRefund() : false)
                //premium service benefits
                .loungeAccess(fare.getPremiumServiceBenefits() != null ? fare.getPremiumServiceBenefits().getLoungeAccess(): false)
                .airportTransfer(fare.getPremiumServiceBenefits() !=null ? fare.getPremiumServiceBenefits().getAirportTransfer() :false)
                //nested response
                .fareRules(fare.getFareRules() !=null ? FareRulesMapper.toDTO(fare.getFareRules()) : null)
                .baggagePolicy(fare.getBaggagePolicy() !=null ? BaggagePolicyMapper.toDTO(fare.getBaggagePolicy()) :null)
                .createdAt(fare.getCreatedAt())
                .updatedAt(fare.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FareRequest request, Fare existing){
        if (request ==null || existing ==null) return;
        if (request.getName() !=null) existing.setName(request.getName());
        if (request.getRbdCode() !=null) existing.setRdbCode(request.getRbdCode());
        if (request.getFlightId() !=null) existing.setFlightId(request.getFlightId());
        if (request.getCabinClassId() !=null) existing.setCabinClassId(request.getCabinClassId());

        if (request.getBaseFare() !=null) existing.setBaseFare(request.getBaseFare());
        if (request.getTaxesAndFees() !=null) existing.setTaxesAndFees(request.getTaxesAndFees());
        if (request.getAirlineFees() !=null) existing.setAirlineFees(request.getAirlineFees());
        if (request.getCurrentPrice() !=null) existing.setCurrentPrice(request.getCurrentPrice());
        if (request.getFareLabel() !=null) existing.setFareLabel(request.getFareLabel());

        //Update embedded benefits
        SeatBenefits sb=existing.getSeatBenefits();
        if (request.getExtraSeatSpace() !=null) sb.setExtraSeatSpace(request.getExtraSeatSpace());
        if (request.getPreferredSeatChoice() !=null) sb.setPreferredSeatChoice(request.getPreferredSeatChoice());
        if (request.getAdvanceSeatSelection() !=null) sb.setAdvanceSeatSelection(request.getAdvanceSeatSelection());
        if (request.getGuaranteedSeatTogether() !=null) sb.setGuaranteedSeatTogether(request.getGuaranteedSeatTogether());

        BoardingBenefits bb=existing.getBoardingBenefits();
        if (request.getPriorityBoarding() !=null) bb.setPriorityBoarding(request.getPriorityBoarding());
        if (request.getPriorityChecking() !=null) bb.setPriorityChecking(request.getPriorityChecking());
        if (request.getFastTrackSecurity() !=null) bb.setFastTrackSecurity(request.getFastTrackSecurity());

        InFlightBenefits ifb=existing.getInFlightBenefits();
        if (request.getComplimentaryMeals() !=null) ifb.setComplimentaryMeals(request.getComplimentaryMeals());
        if (request.getPremiumMealChoice() !=null) ifb.setPremiumMealChoice(request.getPremiumMealChoice());
        if (request.getInFlightInternet() !=null) ifb.setInFlightInternet(request.getInFlightInternet());
        if (request.getInFlightEntertainment() !=null) ifb.setInFlightEntertainment(request.getInFlightEntertainment());
        if (request.getComplimentaryBeverages() !=null) ifb.setComplimentaryBeverage(request.getComplimentaryBeverages());

        FlexibilityBenefits fb=existing.getFlexibilityBenefit();
        if (request.getFreeDataChange() !=null) fb.setFreeDataChange(request.getFreeDataChange());
        if (request.getPartialRefund() !=null) fb.setPartialRefund(request.getPartialRefund());
        if (request.getFullRefund() !=null) fb.setFullRefund(request.getFullRefund());

        PremiumServiceBenefits psb=existing.getPremiumServiceBenefits();
        if (request.getLoungeAccess() !=null) psb.setLoungeAccess(request.getLoungeAccess());
        if (request.getAirportTransfer() !=null) psb.setAirportTransfer(request.getAirportTransfer());
    }

    private static boolean bool(Boolean bool) {
        //null = false !null bool
        return bool != null ? bool : false;
    }
}

