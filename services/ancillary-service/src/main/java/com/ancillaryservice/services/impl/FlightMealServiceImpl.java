package com.ancillaryservice.services.impl;

import com.ancillaryservice.mapper.FlightMealMapper;
import com.ancillaryservice.model.FlightMeal;
import com.ancillaryservice.model.Meal;
import com.ancillaryservice.repository.FlightMealRepository;
import com.ancillaryservice.repository.MealRepository;
import com.ancillaryservice.services.FlightMealService;
import com.payload.request.FlightMealRequest;
import com.payload.response.FlightMealResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightMealServiceImpl implements FlightMealService {

    private final FlightMealRepository flightMealRepository;
    private final MealRepository mealRepository;

    @Override
    public FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception {
        Meal meal=mealRepository.findById(request.getMealId())
                .orElseThrow(()->new Exception("Meal not found"));
        if (flightMealRepository.existsByFlightIdAndMealId(request.getMealId(),meal.getId())){
            throw new Exception("Meal already exists");
        }
        FlightMeal flightMeal=FlightMeal.builder()
                .flightId(request.getFlightId())
                .meal(meal)
                .available(request.getAvailable())
                .price(request.getPrice())
                .displayOrder(request.getDisplayOrder() !=null ? request.getDisplayOrder() : null)
                .build();
        FlightMeal saved=flightMealRepository.save(flightMeal);
        return FlightMealMapper.toDto(saved);
    }

    @Override
    public FlightMealResponse getFlightMealById(Long id) throws Exception {
        FlightMeal flightMeal=flightMealRepository.findById(id)
                .orElseThrow(()->new Exception("Flight meal not found"));
        return FlightMealMapper.toDto(flightMeal);
    }

    @Override
    public List<FlightMealResponse> getByFlightId(Long flightId) {
        return flightMealRepository.findByFlightId(flightId)
                .stream()
                .map(FlightMealMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightMealResponse> getAllByIds(List<Long> ids) {
        return flightMealRepository.findAllById(ids)
                .stream()
                .map(FlightMealMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) throws Exception {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight meal not found"));
        flightMeal.setFlightId(request.getFlightId());
        if (request.getMealId() !=null){
            Meal meal=mealRepository.findById(request.getMealId())
                    .orElseThrow(()->new Exception("Meal not found"));
            flightMeal.setMeal(meal);
        }
        flightMeal.setAvailable(request.getAvailable());
        flightMeal.setPrice(request.getPrice());
        flightMeal.setDisplayOrder(request.getDisplayOrder());
        FlightMeal saved=flightMealRepository.save(flightMeal);
        return FlightMealMapper.toDto(saved);
    }

    @Override
    public void deleteFlightMeal(Long id) throws Exception {
        FlightMeal flightMeal=flightMealRepository.findById(id)
                .orElseThrow(()->new Exception("Flight meal not found"));
        flightMealRepository.delete(flightMeal);

    }

    @Override
    public FlightMealResponse updateFlightMealAvailability(Long id, Boolean availability) throws Exception {
        FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight meal not found"));
        flightMeal.setAvailable(availability);
        FlightMeal saved = flightMealRepository.save(flightMeal);
        return FlightMealMapper.toDto(saved);
    }

    @Override
    public Double calculatePrice(List<Long> mealIds) {
        List<FlightMeal> meals=flightMealRepository.findAllById(mealIds);
        double price=0.0;
        for (FlightMeal meal:meals){
            price +=meal.getPrice();
        }
        return price;
    }
}
