package com.paymentservice.service;

import com.payload.dto.PaymentDTO;
import com.payload.request.PaymentInitiateRequest;
import com.payload.request.PaymentVerifyRequest;
import com.payload.response.PaymentInitiateResponse;
import com.razorpay.RazorpayException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException;

    PaymentDTO verifyPayment(PaymentVerifyRequest request) throws Exception;

    Page<PaymentDTO> getAllPayments(Pageable pageable);

    Map<Long,PaymentDTO> getPaymentsByBookingIds(List<Long> bookingsIds);



}
