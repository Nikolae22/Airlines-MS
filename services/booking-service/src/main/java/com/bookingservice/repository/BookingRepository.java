package com.bookingservice.repository;


import com.bookingservice.model.Booking;
import com.enums.BookingStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);
    long countByFlightInstanceId(Long flightInstanceId);

    @Query("""
       select distinc b from Booking b
       left join fetch b.passengers p
       where b.airlineId = :airlineId
              and (:search is null or Lower(b.bookingReference) like Lower(concat('%', :search, '%'))
                     or lower(p.firstName) like Lower(concat('%', :search, '%'))
                     or lower(p.lastName) like Lower(concat('%', :search, '%'))
                     or lower(p.email)  like Lower(concat('%', :search, '%'))
                    or lower(b.contactInfo.email)  like Lower(concat('%', :search, '%'))
                    or lower(b.contactInfo.phone)  like Lower(concat('%', :search, '%')))
                and (:status is null or b.status = :status)
                and (:flightInstanceId is null or b.flightInstanceId = :flightInstanceId)
       """)
    List<Booking> findByAirlineWithFilter(
            @Param("airlineId") Long airlineId,
            @Param("searchQuery") String searchQuery,
            @Param("status") BookingStatus status,
            @Param("flightInstanceId") Long flightInstanceId,
            Sort sort
    );

    boolean existsByBookingReference(String bookingReference);
}
