package com.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingConfirmedEvent {

    //booking
    private Long bookingId;
    private String bookingReference;
    private LocalDateTime confirmedAt;
    private LocalDateTime bookingDate;
    private String cabinClass;

    private boolean flexibleTicket;

    //contact
    private Long userId;
    private String userName;
    private String contactEmail;
    private String contactPhone;

    //passengets
    private List<PassengerNotificationData> passengers;

    //flight
    private Long flightInstanceId;
    private String flightNumber;
    private String airlineName;
    private String airlineLogo;
    private String aircraftModel;


    //departure
    private String departureAirportCode;
    private String departureAirportName;
    private String departureCity;
    private String departureCountry;
    private LocalDateTime departureDateTime;

    //arrival
    private String arrivalAirportCode;
    private String arrivalAirportName;
    private String arrivalCity;
    private String arrivalCountry;
    private LocalDateTime arrivalDateTime;
    private String flightDuration;

    //payment
    private Double totalAmount;
    private String currency;
    private String transactionId;
    private String providerPaymentId;
    private String paymentGateway;
    private LocalDateTime paidAt;

    //fare
    private String fareName;
    private Double baseFare;
    private Double taxesAndFees;
    private Double seatFees;
    private Double ancillaryFees;
    private Double mealFees;

    //baggage allownce
    private Integer checkingBaggagePieces;
    private Double checkingBaggageWeightPerPiece;
    private Integer cabinBaggagePieces;
    private Double cabinBaggageWeightPerPieces;

    //fare benefits
    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;
    private Boolean priorityBoarding;
    private Boolean loungeAccess;
    private Boolean complimentaryMeals;

    private List<Long> seatInstanceIds;
    //
}
