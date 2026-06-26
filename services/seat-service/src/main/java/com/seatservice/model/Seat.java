package com.seatservice.model;

import com.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Integer seatRow;

    private Character columnLetter;

    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;


    private Double basePrice;

    private Double premiumSuperCharge;

    private Boolean isAvailable=true;

    private Boolean isBlocked=false;

    private Boolean isEmergencyExist= false;

    private Boolean isActive=true;

    private Boolean hasExtraLegRoom=false;

    private Boolean hasPowerOutlet=false;

    private Boolean hasTvScreen=false;

    private Boolean hasExtraWidth=false;

    private Integer seatPitch;
    private Integer seatWidth;

    @ManyToOne
    private SeatMap seatMap;

    @ManyToOne
    private CabinClass cabinClass;

    @CreationTimestamp
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    //serve per fare il check cosi due perosne non prenotono la stessa sedia
    @Version
    private Long version;

    private Double getTotalPrice(){
        Double total=basePrice!=null ? basePrice :0;
        if (premiumSuperCharge !=null){
            total+=premiumSuperCharge;
        }
        return total;
    }

     public boolean isBookable(){
        return isActive && isAvailable && !isBlocked;
     }

     public String getFullPosition(){
        return seatRow + ""+columnLetter;
     }


}
