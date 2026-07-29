package com.bookingservice.services;

import com.enums.BookingStatus;
import com.payload.request.BookingRequest;
import com.payload.response.BookingResponse;
import com.payload.response.PaymentInitiateResponse;

import java.util.List;

public interface BookingService {

    PaymentInitiateResponse createBooking(BookingRequest bookingRequest, Long userId);
    BookingResponse updateBooking(Long bookingId,BookingRequest bookingRequest);
    BookingResponse getBookingById(Long bookingId) throws Exception;
    List<BookingResponse> getAllBookingsByAirline(Long userId,
                                                  String searchQuery,
                                                  BookingStatus status,
                                                  Long flightInstanceId,
                                                  String sortDirection);
    List<BookingResponse> getBookingsByUser(Long userId);
    BookingResponse cancelBooking(Long id) throws Exception;

    void deleteBooking(Long id) throws Exception;
}
