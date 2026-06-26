package com.seatservice.model;

import com.enums.CabinClassType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CabinClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CabinClassType name;

    @Column(nullable = false)
    private String code;

    private String description;

    @OneToOne(mappedBy = "cabinClass",cascade = CascadeType.ALL,orphanRemoval = true)
    private SeatMap seatMap;

    @Column(nullable = false)
    private Long aircraftId;

    @Column(nullable = false)
    private Integer displayOrder=0;

    @Column(nullable = false)
    private Boolean isActive=true;

    @Column(nullable = false)
    private Boolean isBookable=true;

    private Integer typicalSeatPitch;
    private Integer typicalSeatWidth;
    private String seatType;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
}
