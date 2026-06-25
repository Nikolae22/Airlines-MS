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
public class PremiumServiceBenefits {

    @Column(nullable = false)
    @Builder.Default
    private Boolean loungeAccess=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean airportTransfer=false;
}
