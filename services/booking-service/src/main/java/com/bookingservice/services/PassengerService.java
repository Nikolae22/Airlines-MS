package com.bookingservice.services;

import com.bookingservice.model.Passenger;
import com.payload.request.PassengerRequest;

public interface PassengerService {

    Passenger createPassenger(PassengerRequest request,Long userId);

}
