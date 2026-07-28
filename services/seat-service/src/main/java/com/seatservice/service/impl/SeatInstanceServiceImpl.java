package com.seatservice.service.impl;

import com.seatservice.model.SeatInstance;
import com.seatservice.repository.SeatInstanceRepository;
import com.seatservice.service.SeatInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatInstanceServiceImpl implements SeatInstanceService {
    private final SeatInstanceRepository seatInstanceRepository;


    @Override
    public Double calculateSeatPrice(List<Long> seatInstanceIds) {
        List<SeatInstance> seatInstances=seatInstanceRepository.findAllById(seatInstanceIds);
        double price=0;
        for (SeatInstance si:seatInstances){
            double seatPremium=si.getPremiumSuperCharge() !=null ?
                    si.getPremiumSuperCharge() : 0;
            price +=seatPremium;
        }
        return price;
    }
}
