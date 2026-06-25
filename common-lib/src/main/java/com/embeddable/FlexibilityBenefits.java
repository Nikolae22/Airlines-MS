package com.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlexibilityBenefits {

    protected Boolean freeDataChange=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean partialRefund=false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean fullRefund=false;
}
