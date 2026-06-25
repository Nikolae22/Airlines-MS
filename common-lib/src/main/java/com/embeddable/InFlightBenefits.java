package com.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InFlightBenefits {

    @Column(nullable = false)
    @Builder.Default
    private Boolean complimentaryMeals=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean premiumMealChoice=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean inFlightInternet=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean inFlightEntertainment=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean complimentaryBeverage=false;
}
