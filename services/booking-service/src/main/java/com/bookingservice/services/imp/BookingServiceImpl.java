package com.bookingservice.services.imp;

import com.bookingservice.mapper.BookingMapper;
import com.bookingservice.model.Booking;
import com.bookingservice.model.Passenger;
import com.bookingservice.repository.BookingRepository;
import com.bookingservice.services.BookingService;
import com.bookingservice.services.PassengerService;
import com.bookingservice.services.TicketService;
import com.enums.BookingStatus;
import com.payload.dto.PaymentDTO;
import com.payload.request.BookingRequest;
import com.payload.request.PassengerRequest;
import com.payload.response.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.property.access.internal.PropertyAccessGetterImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerService passengerService;
    private final TicketService ticketService;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest, Long userId) {
        //1 create uniq booking referemce
        String bookingReference = generateBookingReference();
        //2ceaete passenger
        Set<Passenger> passengers=new HashSet<>();
        for (PassengerRequest passengerRequest: bookingRequest.getPassengers()){
            Passenger passenger=passengerService.createPassenger(passengerRequest,userId);
            passengers.add(passenger);
        }
        // 3 todo check flifht exists
        // 4 create booking with pending
        Booking booking= BookingMapper.toEntity(bookingRequest,
                userId,passengers,bookingReference);
        //todo set ariline id from flightresponse
        booking.setAirlineId(1L);

        // 5 set sesat instance ids
        List<Long> seatInstanceIds= bookingRequest.getPassengers().stream()
                .map(PassengerRequest::getSeatInstanceId)
                .collect(Collectors.toList());
        booking.setSeatInstanceIds(seatInstanceIds);

        booking= bookingRepository.save(booking);

        //set booking reference to passengers
        for (Passenger passenger:passengers){
            passenger.setBooking(booking);
        }

        // 6 generate tickets for booking
        ticketService.generateTicketsForBooking(booking);

        //7 todo calculate price

        // 8 todo initiate payment using payment service


        return convertToBookingResponse(booking);
    }

    @Override
    public BookingResponse updateBooking(Long bookingId, BookingRequest bookingRequest) {
        return null;
    }

    @Override
    public BookingResponse getBookingById(Long bookingId) throws Exception {
        Booking booking= bookingRepository.findById(bookingId)
                .orElseThrow(()->new Exception("Booking not found with this id"));
        return convertToBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookingsByAirline(Long airlineId, String searchQuery, BookingStatus status,
                                                         Long flightInstanceId, String sortDirection) {
        Sort.Direction direction="asc".equalsIgnoreCase(sortDirection)?Sort.Direction.ASC: Sort.Direction.DESC;
        Sort sort=Sort.by(direction,"bookingDate");
        List<Booking> bookings=bookingRepository.findByAirlineWithFilter(
                airlineId,searchQuery,status,flightInstanceId,sort);
        return bookings.stream().map(
                this::convertToBookingResponse
        ).toList();

    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream().map(this::convertToBookingResponse).toList();
    }

    @Override
    public BookingResponse cancelBooking(Long id) throws Exception {
        Booking booking= bookingRepository.findById(id)
                .orElseThrow(()->new Exception("Booking not found with this id"));
        booking.setStatus(BookingStatus.CANCELLED);
        Booking updated=bookingRepository.save(booking);
        return convertToBookingResponse(updated);
    }

    @Override
    public void deleteBooking(Long id) throws Exception {
        Booking booking= bookingRepository.findById(id)
                .orElseThrow(()->new Exception("Booking not found with this id"));
        bookingRepository.delete(booking);

    }

    private String generateBookingReference() {
       String reference;
       do {
           reference= "BK"+ UUID.randomUUID().toString().substring(0, 8).toUpperCase();
       }while (bookingRepository.existsByBookingReference(reference));
        return reference;
    }


    private BookingResponse convertToBookingResponse(Booking booking){
        //todo when enabele feign client use actual response
        List<FlightCabinAncillaryResponse> ancillaryResponses=new ArrayList<>();
        List<FlightMealResponse> mealResponses=new ArrayList<>();
        PaymentDTO paymentDTO=new PaymentDTO();
        FareResponse fareResponse=new FareResponse();
        FlightResponse flightResponse=new FlightResponse();

        List<SeatInstanceResponse> seatInstanceResponses=new ArrayList<>();
        FlightInstanceResponse flightInstanceResponse=new FlightInstanceResponse();

        return BookingMapper.toDTO(
                booking,paymentDTO,fareResponse,flightResponse,flightInstanceResponse,
                ancillaryResponses,mealResponses,seatInstanceResponses
        );
    }
}
