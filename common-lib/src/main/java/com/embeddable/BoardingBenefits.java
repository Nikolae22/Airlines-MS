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
public class BoardingBenefits {

    @Column(nullable = false)
    @Builder.Default
    private Boolean priorityBoarding=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean priorityChecking=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean fastTrackSecurity=false;
}
