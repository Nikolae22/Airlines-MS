package com.ancillaryservice.services.impl;

import com.ancillaryservice.mapper.AncillaryMapper;
import com.ancillaryservice.model.Ancillary;
import com.ancillaryservice.repository.AncillaryRepository;
import com.ancillaryservice.services.AncillaryService;
import com.payload.request.AncillaryRequest;
import com.payload.response.AncillaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AncillaryServiceImpl implements AncillaryService {

    private final AncillaryRepository ancillaryRepository;

    @Override
    public AncillaryResponse createAncillary(Long airlineId, AncillaryRequest request) {
        Ancillary ancillary=Ancillary.builder()
                .type(request.getType())
                .subType(request.getSubType())
                .rfisc(request.getRfisc())
                .name(request.getName())
                .description(request.getDescription())
                .metadata(request.getMetadata())
                .displayOrder(request.getDisplayOrder())
                .airlineId(airlineId)
                .build();

        Ancillary saved = ancillaryRepository.save(ancillary);
        return AncillaryMapper.toDTO(saved,null);
    }

    @Override
    public AncillaryResponse getById(Long id) throws Exception {
        Ancillary ancillary=ancillaryRepository.findById(id)
                .orElseThrow(()->new Exception("Ancillary not found"));

        //todo
        //fetch insurance converages by ancillary
        return AncillaryMapper.toDTO(ancillary,null);
    }

    @Override
    public List<AncillaryResponse> getByAirlineId(Long airlineId) {
        return ancillaryRepository.findByAirlineId(airlineId)
                .stream()
                .map(ancillary -> {
                    //todo fetch insurance coverage by ancillary
                    return AncillaryMapper.toDTO(ancillary,null);
                }).toList();
    }

    @Override
    public AncillaryResponse updateAncillary(Long id, AncillaryRequest request) throws Exception {
        Ancillary ancillary=ancillaryRepository.findById(id)
                .orElseThrow(()->new Exception("Ancillary not found"));
        ancillary.setType(request.getType());
        ancillary.setSubType(request.getSubType());
        ancillary.setRfisc(request.getRfisc());
        ancillary.setName(request.getName());
        ancillary.setDescription(request.getDescription());
        ancillary.setMetadata(request.getMetadata());
        ancillary.setDisplayOrder(request.getDisplayOrder());
        Ancillary saved = ancillaryRepository.save(ancillary);
        //todo fetch insurance converages by ancillary
        return AncillaryMapper.toDTO(saved,null);
    }

    @Override
    public void deleteAncillary(Long id) throws Exception {
        Ancillary ancillary=ancillaryRepository.findById(id)
                .orElseThrow(()->new Exception("Ancillary not found"));
        ancillaryRepository.delete(ancillary);
    }
}
