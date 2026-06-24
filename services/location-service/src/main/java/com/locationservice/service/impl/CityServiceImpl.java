package com.locationservice.service.impl;

import com.locationservice.mapper.CityMapper;
import com.locationservice.model.City;
import com.locationservice.payload.request.CityRequest;
import com.locationservice.payload.response.CityResponse;
import com.locationservice.repository.CityRepository;
import com.locationservice.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) throws Exception {

        if (cityRepository.existsByCityCode(request.getCityCode())){
            throw new Exception("City with given code already exists");
        }
        City city= CityMapper.toEntity(request);
        City saved = cityRepository.save(city);
        return CityMapper.toDTO(saved);
    }

    @Override
    public CityResponse getCityById(Long id) throws Exception {
      City city=  cityRepository.findById(id)
                .orElseThrow(()->new Exception("City not exit with given id"));
        return CityMapper.toDTO(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
        City city=  cityRepository.findById(id)
                .orElseThrow(()->new Exception("City not exit with given id"));
        if (cityRepository.existsByCityCodeAndIdNot(request.getCityCode(),id)){
            throw new Exception("City with given code already exists");
        }

        City updatedCity=cityRepository.save(CityMapper.updateEntity(city,request));
        return CityMapper.toDTO(updatedCity);
    }

    @Override
    public void deleteCity(Long id) throws Exception {
        City city=  cityRepository.findById(id)
                .orElseThrow(()->new Exception("City not exit with given id"));
        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toDTO);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::toDTO);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode,pageable)
                .map(CityMapper::toDTO);
    }

    @Override
    public boolean cityExists(String cityCode) {
       return cityRepository.existsByCityCode(cityCode);
    }

}
