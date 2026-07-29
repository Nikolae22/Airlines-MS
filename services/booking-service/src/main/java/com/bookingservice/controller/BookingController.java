package com.bookingservice.controller;

import com.bookingservice.services.BookingService;
import com.enums.BookingStatus;
import com.payload.request.BookingRequest;
import com.payload.response.ApiResponse;
import com.payload.response.BookingResponse;
import com.payload.response.PaymentInitiateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @PostMapping
    public ResponseEntity<PaymentInitiateResponse> createBooking(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody BookingRequest bookingRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(
                        bookingRequest,userId
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<List<BookingResponse>> getBookingsByAirline(
            @RequestParam(required = false) String search,
            @RequestParam(required = false)BookingStatus status,
            @RequestParam(required = false) Long flightInstanceId,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.ok(
                bookingService.getAllBookingsByAirline(
                        userId,search,status,flightInstanceId,sortDirection
                )
        );
    }

    @GetMapping("/user/history")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(
            @RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.ok(
                bookingService.getBookingsByUser(userId)
        );
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(bookingService.cancelBooking(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBooking(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(new ApiResponse("Booking deleted"));
    }


}
