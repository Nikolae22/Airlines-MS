package com.paymentservice.service.impl;

import com.enums.PaymentGateway;
import com.enums.PaymentStatus;
import com.payload.dto.PaymentDTO;
import com.payload.dto.UserDTO;
import com.payload.request.PaymentInitiateRequest;
import com.payload.request.PaymentVerifyRequest;
import com.payload.response.PaymentInitiateResponse;
import com.payload.response.PaymentLinkResponse;
import com.paymentservice.client.UserClient;
import com.paymentservice.event.PaymentEventProducer;
import com.paymentservice.mapper.PaymentMapper;
import com.paymentservice.model.Payment;
import com.paymentservice.reposiotry.PaymentRepository;
import com.paymentservice.service.PaymentService;
import com.paymentservice.service.gateway.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final PaymentEventProducer paymentEventProducer;
    private final UserClient userClient;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException {
        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(payment -> {
                    if (payment.getStatus() == PaymentStatus.SUCCESS){
                        throw new RuntimeException("Payment already completed for this booking");
                    }
                });

        Payment payment=Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .provider(request.getGateway())
                .status(PaymentStatus.PENDING)
                .transactionId(generateTransactionId())
                .build();
        payment=paymentRepository.save(payment);

        PaymentInitiateResponse response=PaymentInitiateResponse.builder()
                .paymentId(payment.getId())
                .gateway(request.getGateway())
                .transactionId(payment.getTransactionId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .success(true)
                .message("PAyment initiated successfulyy")
                .build();

        if (request.getGateway() == PaymentGateway.RAZORPAY){
            //fetch user details using feign client
            UserDTO userDTO=userClient.getUserById(request.getUserId());
//            UserDTO userDTO=new UserDTO();
//            userDTO.setId(1L);
//            userDTO.setFullName("Pablo Emilio");
//            userDTO.setEmail("we@we.com");
//            userDTO.setPhone("123456");
            //create razorpay payment link using razorpay service
            PaymentLinkResponse paymentLinkResponse=razorpayService.createPaymentLink(
                    userDTO,payment);

            // set paymeny link to payment iniziate response
            response.setRazorpayOrderId(paymentLinkResponse.getPayment_link_id());
            response.setCheckoutUrl(paymentLinkResponse.getPayment_link_url());

        }
        return response;
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest request) throws Exception {
        JSONObject paymentDetails=razorpayService.fetchPaymentDetails(
                request.getRazorpayPaymentId());

        String status= paymentDetails.getString("status");

        JSONObject notes=paymentDetails.getJSONObject("notes");
        Long paymentId=Long.parseLong(notes.optString("payment_id"));

        Payment payment=paymentRepository.findById(paymentId)
                .orElseThrow(()->new Exception("Payment not found"));

        boolean isValid="captured".equalsIgnoreCase(status);
        if (isValid){
            if (payment.getProvider() == PaymentGateway.RAZORPAY){
                payment.setProviderPaymentId(request.getRazorpayPaymentId());
            }
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            //publish kafka event success payment
            paymentEventProducer.sendPaymentCompleted(payment);
        }else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment verification failed");
            paymentRepository.save(payment);

            //publish kafka event payment failed
            paymentEventProducer.sendPaymentFailed(payment);

        }
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(PaymentMapper::toDTO);
    }

    @Override
    public Map<Long, PaymentDTO> getPaymentsByBookingIds(List<Long> bookingsIds) {
        return paymentRepository.findByBookingsIdIn(bookingsIds)
                .stream()
                .collect(Collectors.toMap(
                        Payment::getId,
                        PaymentMapper::toDTO
                ));
    }


    private String generateTransactionId(){
        return "TXN_"+System.currentTimeMillis()+"_"+
                UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }
}
