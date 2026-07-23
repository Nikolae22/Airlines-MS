package com.bookingservice.mapper;

import com.bookingservice.model.Passenger;
import com.payload.request.PassengerRequest;
import com.payload.response.PassengerResponse;

public class PassengerMapper {


    public static Passenger toEntity(PassengerRequest request){
        return Passenger.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .nationality(request.getNationality())
                .build();
    }

    public static PassengerResponse toDTO(Passenger passenger){
        return PassengerResponse.builder()
                .id(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .email(passenger.getEmail())
                .phone(passenger.getPhone())
                .dateOfBirth(passenger.getDateOfBirth())
                .gender(passenger.getGender())
                .nationality(passenger.getNationality())
                .primaryUserId(passenger.getPrimaryUserId())
                .isActive(passenger.getIsActive())
                .age(passenger.getAge())
                .isAdult(passenger.isAdult())
                .fullName(passenger.getFullName())
                .createdAt(passenger.getCreatedAt())
                .updatedAt(passenger.getUpdatedAt())
                .build();
    }
}
