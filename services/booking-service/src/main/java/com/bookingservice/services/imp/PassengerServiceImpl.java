package com.bookingservice.services.imp;

import com.bookingservice.mapper.PassengerMapper;
import com.bookingservice.model.Passenger;
import com.bookingservice.repository.PassengerRepository;
import com.bookingservice.services.PassengerService;
import com.payload.request.PassengerRequest;
import com.payload.response.PassengerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Override
    public Passenger createPassenger(PassengerRequest request, Long userId) {
        Passenger passenger= PassengerMapper.toEntity(request);
        passenger.setPrimaryUserId(userId);
        Passenger saved = passengerRepository.save(passenger);
        return saved;
    }



}
