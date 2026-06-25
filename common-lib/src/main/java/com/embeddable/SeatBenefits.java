package com.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatBenefits {

    private Boolean extraSeatSpace=false;
    private Boolean preferredSeatChoice=false;
    private Boolean advanceSeatSelection =false;
    private Boolean guaranteedSeatTogether=false;
}
