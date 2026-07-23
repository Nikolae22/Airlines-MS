package com.paymentservice.controller;

import com.payload.dto.PaymentDTO;
import com.payload.request.PaymentInitiateRequest;
import com.payload.request.PaymentVerifyRequest;
import com.payload.response.PaymentInitiateResponse;
import com.paymentservice.service.PaymentService;
import com.razorpay.RazorpayException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(
           @Valid @RequestBody PaymentInitiateRequest request) throws RazorpayException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.initiatePayment(request));
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequest request) throws Exception {
        log.info("Received payment verification reqeusts");
        return ResponseEntity.ok(paymentService.verifyPayment(request));
    }


    @PostMapping("/batch/bookings")
    public ResponseEntity<Map<Long, PaymentDTO>> getPaymentsByBookingsIds(
            @RequestBody List<Long> bookingsIds){
        return ResponseEntity.ok(paymentService.getPaymentsByBookingIds(bookingsIds));
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
           // @RequestHeader("X-User-Id") Long userId
    ){
        Sort.Direction direction=sortDirection.equalsIgnoreCase("ASC")?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,sortBy));
        Page<PaymentDTO> payments= paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }


}
