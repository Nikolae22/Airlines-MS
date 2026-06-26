package com.seatservice.service.impl;

import com.enums.CabinClassType;
import com.payload.request.CabinClassRequest;
import com.payload.response.CabinClassResponse;
import com.seatservice.mapper.CabinClassMapper;
import com.seatservice.model.CabinClass;
import com.seatservice.repository.CabinClassRepository;
import com.seatservice.service.CabinClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CabinClassServiceImpl implements CabinClassService {

    private final CabinClassRepository cabinClassRepository;

    @Override
    public CabinClassResponse createCabinClass(CabinClassRequest request) throws Exception {
        if (cabinClassRepository.existsByCodeAndAircraftId(request.getCode(),request.getAircraftId())){
            throw new Exception("Code already exists");
        }
        CabinClass cabinClass= CabinClassMapper.toEntity(request);
        CabinClass saved = cabinClassRepository.save(cabinClass);
        return CabinClassMapper.toDTO(saved,null);
    }

    @Override
    public CabinClassResponse getCabinClassById(Long id) throws Exception {
        CabinClass cabinClass=cabinClassRepository.findById(id)
                .orElseThrow(()->new Exception("Cabin class not found with this is"));
        return CabinClassMapper.toDTO(cabinClass,cabinClass.getSeatMap());
    }

    @Override
    public List<CabinClassResponse> getCabinClassesByAircraftId(Long aircraftId) {
        return cabinClassRepository.findByAircraftId(aircraftId)
                .stream()
                .map(cc->CabinClassMapper.toDTO(cc,cc.getSeatMap()))
                .collect(Collectors.toList());
    }

    @Override
    public CabinClassResponse getByAircraftIdAndName(Long aircraft, CabinClassType name) {
        CabinClass cabinClass=cabinClassRepository.findByAircraftIdAndName(aircraft,name);
        return CabinClassMapper.toDTO(cabinClass,null);
    }

    @Override
    public CabinClassResponse updateCabinClass(Long id, CabinClassRequest request) throws Exception {
        CabinClass cabinClass=cabinClassRepository.findById(id)
                .orElseThrow(()->new Exception("Cabin class not found with this is"));

        //existsin cabin code => PE
        // reqeust update =>BC

        //existingg cabin code => BC
        //request update => BC, but different name

        if (cabinClassRepository.existsByCodeAndAircraftIdAndIdNot(
                request.getCode().toUpperCase(),cabinClass.getAircraftId()
                ,cabinClass.getId())){
            throw new Exception("Cabin class with this code already exists");
        }
        CabinClassMapper.updateEntity(request,cabinClass);
        CabinClass saved = cabinClassRepository.save(cabinClass);
        return CabinClassMapper.toDTO(saved,saved.getSeatMap());
    }

    @Override
    public void deleteCabinClass(Long id) throws Exception {
        CabinClass cabinClass=cabinClassRepository.findById(id)
                .orElseThrow(()->new Exception("Cabin class not found with this is"));
        cabinClassRepository.delete(cabinClass);
    }
}
