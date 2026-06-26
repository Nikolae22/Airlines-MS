package com.seatservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class FlightInstanceCabin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long flightInstanceId;

    @ManyToOne
    private CabinClass cabinClass;

    @Column(nullable = false)
    private Integer totalSeats;

    private Integer bookedSeats=0;

    @OneToMany(mappedBy = "flightInstanceCabin",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SeatInstance> seats=new ArrayList<>();

    public Integer getAvailableSeats(){
        return totalSeats-bookedSeats;
    }
}
