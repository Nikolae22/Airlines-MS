package com.ancillaryservice.services.impl;

import com.ancillaryservice.mapper.InsuranceCoverageMapper;
import com.ancillaryservice.model.Ancillary;
import com.ancillaryservice.model.InsuranceCoverage;
import com.ancillaryservice.repository.AncillaryRepository;
import com.ancillaryservice.repository.InsuranceCoverageRepository;
import com.ancillaryservice.services.InsuranceCoverageService;
import com.payload.request.InsuranceCoverageRequest;
import com.payload.response.InsuranceCoverageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceCoverageServiceImpl implements InsuranceCoverageService {

    private final InsuranceCoverageRepository insuranceCoverageRepository;
    private final AncillaryRepository ancillaryRepository;

    @Override
    public InsuranceCoverageResponse createCoverage(InsuranceCoverageRequest request) throws Exception {
        Ancillary ancillary=ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(()->new Exception("Ancillary not found with this id"));
        InsuranceCoverage coverage= InsuranceCoverageMapper.toEntity(request,ancillary);
        InsuranceCoverage saved = insuranceCoverageRepository.save(coverage);
        return InsuranceCoverageMapper.toDTO(saved);
    }

    @Override
    public InsuranceCoverageResponse updateCoverage(Long id, InsuranceCoverageRequest request) throws Exception {
        InsuranceCoverage insuranceCoverage=insuranceCoverageRepository.findById(id)
                .orElseThrow(()->new Exception("Insurance converage not found with this id"));
        Ancillary ancillary=null;
        if (request.getAncillaryId() !=null) {
            ancillary = ancillaryRepository.findById(request.getAncillaryId())
                    .orElseThrow(() -> new Exception("Ancillary not found with this id"));
        }
        InsuranceCoverageMapper.updateEntityFromRequest(insuranceCoverage,request, ancillary);
        InsuranceCoverage saved = insuranceCoverageRepository.save(insuranceCoverage);
        return InsuranceCoverageMapper.toDTO(saved);
    }

    @Override
    public void deleteCoverage(Long id) throws Exception {
        InsuranceCoverage insuranceCoverage=insuranceCoverageRepository.findById(id)
                .orElseThrow(()->new Exception("Insurance converage not found with this id"));
        insuranceCoverageRepository.delete(insuranceCoverage);
    }

    @Override
    public InsuranceCoverageResponse getCoverage(Long id) throws Exception {
        InsuranceCoverage insuranceCoverage=insuranceCoverageRepository.findById(id)
                .orElseThrow(()->new Exception("Insurance converage not found with this id"));
        return InsuranceCoverageMapper.toDTO(insuranceCoverage);
    }

    @Override
    public List<InsuranceCoverageResponse> getCoverageByAncillaryId(Long ancillaryId) {
        return insuranceCoverageRepository
                .findByAncillaryId(ancillaryId)
                .stream()
                .map(InsuranceCoverageMapper::toDTO)
                .toList();
    }

    @Override
    public List<InsuranceCoverageResponse> getActiveCoverageByAncillaryId(Long ancillaryId) {
        return insuranceCoverageRepository.findByAncillaryIdAndActiveTrue(ancillaryId)
                .stream().map(
                        InsuranceCoverageMapper::toDTO
                ).toList();
    }

    @Override
    public List<InsuranceCoverageResponse> getAllCoverages() {
        return insuranceCoverageRepository.findAll()
                .stream()
                .map(InsuranceCoverageMapper::toDTO)
                .toList();
    }
}
