package com.seatservice.service.impl;

import com.enums.SeatAvailabilityStatus;
import com.payload.response.SeatInstanceResponse;
import com.seatservice.mapper.SeatInstanceMapper;
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

    @Override
    public SeatInstanceResponse updateSeatInstanceStatus(Long seatInstanceId, SeatAvailabilityStatus status) {
        SeatInstance seatInstance=seatInstanceRepository.findById(seatInstanceId).orElse(null);
        if (seatInstance ==null) {return null;}
        seatInstance.setStatus(status);
        seatInstanceRepository.save(seatInstance);
        return SeatInstanceMapper.toDTO(seatInstance);
    }
}
