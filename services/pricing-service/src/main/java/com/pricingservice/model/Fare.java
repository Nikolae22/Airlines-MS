package com.pricingservice.model;

import com.embeddable.*;
import com.enums.CabinClassType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Character rdbCode;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @Enumerated(EnumType.STRING)
    //todo togli se si usa un altro database
    @Column(name = "cabin_class", columnDefinition = "varchar(255)") // solo per h2
    private CabinClassType cabinClass;

    @Column(nullable = false)
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;

    @Column(nullable = false)
    private Double currentPrice;

    private String fareLabel;

    @OneToOne(mappedBy = "fare",cascade = CascadeType.ALL,orphanRemoval = true)
    private BaggagePolicy baggagePolicy;

    @OneToOne(mappedBy = "fare",cascade = CascadeType.ALL,orphanRemoval = true)
    private FareRules fareRules;

    @Embedded
    private SeatBenefits seatBenefits=new SeatBenefits();

    @Embedded
    private BoardingBenefits boardingBenefits=new BoardingBenefits();

    @Embedded
    @Builder.Default
    private InFlightBenefits inFlightBenefits=new InFlightBenefits();

    @Embedded
    @Builder.Default
    private FlexibilityBenefits flexibilityBenefit= new FlexibilityBenefits();

    @Embedded
    @Builder.Default
    private PremiumServiceBenefits premiumServiceBenefits=new PremiumServiceBenefits();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

//    public Double getTotalPrice(){
//        return baseFare + airlineFees+taxesAndFees+currentPrice;
//    }
public Double getTotalPrice() {
    double base = this.baseFare != null ? this.baseFare : 0.0;
    double current = this.currentPrice != null ? this.currentPrice : 0.0;
    double taxes = this.taxesAndFees != null ? this.taxesAndFees : 0.0;
    double fees = this.airlineFees != null ? this.airlineFees : 0.0;

    return base + current + taxes + fees;
}

}
