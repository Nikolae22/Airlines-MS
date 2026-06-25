package com.pricingservice.service.impl;

import com.payload.request.FareRequest;
import com.payload.response.FareResponse;
import com.pricingservice.mapper.FareMapper;
import com.pricingservice.model.Fare;
import com.pricingservice.repository.FareRepository;
import com.pricingservice.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;

    @Override
    public FareResponse createFare(FareRequest request) throws Exception {
        if (fareRepository.existsByFlightIdAndCabinClassIdAndName(
                request.getFlightId(),request.getCabinClassId(),request.getName())){
            throw new Exception("Fare already exits with provided name");
        }
        Fare fare= FareMapper.toEntity(request);
        Fare saved = fareRepository.save(fare);
        return FareMapper.toDTO(saved);
    }

    @Override
    public FareResponse getFareById(Long id) throws Exception {
        Fare fare=fareRepository.findById(id)
                .orElseThrow(()->new Exception("Fare not exits with this id"));
        return FareMapper.toDTO(fare);
    }

    @Override
    public List<FareResponse> getFaresByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) {
        return fareRepository.findByFlightIdAndCabinClassId(
                flightId,cabinClassId
        ).stream().map(
                FareMapper::toDTO
        ).toList();

    }

    @Override
    public FareResponse updateFare(Long id, FareRequest request) throws Exception {
        Fare fare=fareRepository.findById(id)
                .orElseThrow(()->new Exception("Fare not exits with this id"));
        if (fareRepository.existsByFlightIdAndCabinClassIdAndNameAndIdNot(
                request.getFlightId(),request.getCabinClassId(),
                request.getName(),fare.getId()
        ));
        FareMapper.updateEntity(request,fare);
       Fare saved= fareRepository.save(fare);
        return FareMapper.toDTO(saved);
    }

    @Override
    public void deleteFare(Long id) throws Exception {
        Fare fare=fareRepository.findById(id)
                .orElseThrow(()->new Exception("Fare not exits with this id"));
        fareRepository.delete(fare);
    }

    @Override
    public List<Fare> getFares() {
        return fareRepository.findAll();
    }


    /*
    Flight : 101:
    fare 1 -> 5000
    fare 2 -> 4500
    fare 3 -> 6000

    flights =[101, 102, 103]
    cabin calssId= 2 (Economy)
    retunr a list
    [
       flight 101 ->5000
       flight 101 ->4500
       flight 101 ->6000
       flight 102 -> 7000
       flight 102 -> 6500

       { 101 -> fare 4500, 102-> fare 6500}  al psotp del fare ce solo quello che costa di meno

       final risponse
       101 -> FareResponse(price= 4500)
       102 -> FareResponse(price =6500)
     */
    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(List<Long> flightsIds, Long cabinClassId) {
        if (flightsIds == null || flightsIds.isEmpty()) return Map.of();

        List<Fare> fares=fareRepository.findByFlightIdInAndCabinClassId(
                flightsIds,cabinClassId
        );

        // metodo se ce piu di un fare  cosi fa il check tra exitsign e candindate
        Map<Long, FareResponse> result=fares.stream()
                .collect(Collectors.toMap(
                        Fare::getId, fare ->fare,
                        (existing, candidate) ->
                                candidate.getTotalPrice() <existing.getTotalPrice()
                        ? candidate:existing
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e->FareMapper.toDTO(e.getValue())
                ));

        return result;
//        return fares.stream()
//                .collect(Collectors.toMap(
//                        Fare::getFlightId,       // 1. Usa il flightId come chiave della mappa
//                        FareMapper::toDTO,        // 2. Trasforma direttamente in DTO
//                        (existing, candidate) ->  // 3. Se per lo stesso flightId ci sono più tariffe, tiene la più bassa
//                                candidate.getTotalPrice() < existing.getTotalPrice() ? candidate : existing
//                ));
    }

    @Override
    public Map<Long, FareResponse> getFaresByIds(List<Long> ids) {
        List<Fare> fares=fareRepository.findAllById(ids);

        //[fare response ,fare response]
        // {
        // 1=> fare response
        // 2=> fare response
        // }

      return fares.stream().collect(Collectors.toMap(Fare::getId, FareMapper::toDTO));
    }
}
