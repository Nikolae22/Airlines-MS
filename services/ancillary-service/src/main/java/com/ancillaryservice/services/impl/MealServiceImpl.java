package com.ancillaryservice.services.impl;

import com.ancillaryservice.client.AirlineClient;
import com.ancillaryservice.mapper.MealMapper;
import com.ancillaryservice.model.Meal;
import com.ancillaryservice.repository.MealRepository;
import com.ancillaryservice.services.MealService;
import com.payload.request.MealRequest;
import com.payload.response.AirlineResponse;
import com.payload.response.MealResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {


    private final MealRepository mealRepository;
    private final AirlineClient airlineClient;

    @Override
    public MealResponse createMeal(Long userId,MealRequest request) throws Exception {
        AirlineResponse airlineResp = airlineClient.getAirlineByOwner(userId);
        if (mealRepository.existsByCodeAndAirlineId(request.getCode(),airlineResp.getId())){
            throw new Exception("Meal code already exists");
        }
        Meal meal=Meal.builder()
                .code(request.getCode())
                .name(request.getName())
                .mealType(request.getMealType())
                .dietaryRestriction(request.getDietaryRestriction())
                .ingredients(request.getIngredients())
                .imageUrl(request.getImageUrl())
                .requiresAdvanceBooking(request.getRequiresAdvanceBooking() !=null
                               ? request.getRequiresAdvanceBooking() : false)
                .advanceBookingHours(request.getAdvanceBookingHours())
                .displayOrder(request.getDisplayOrder() !=null ? request.getDisplayOrder():0)
                .airlineId(airlineResp.getId())
                .build();

        Meal saved=mealRepository.save(meal);
        return MealMapper.toDTO(saved);
    }

    @Override
    public MealResponse getMealById(Long id) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("Meal not found")
        );
        return MealMapper.toDTO(meal);
    }

    @Override
    public MealResponse updateMeal(Long userId,Long id, MealRequest request) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("Meal not found")
        );
        AirlineResponse airlineResp = airlineClient.getAirlineByOwner(userId);
        if (meal.getCode() !=null &&
                mealRepository.existsByAirlineIdAndCodeAndIdNot(airlineResp.getId(),request.getCode(),meal.getId())){
            throw new Exception("Meal code alreadt exists");
        }
        meal.setCode(request.getCode());
        meal.setName(request.getName());
        meal.setMealType(request.getMealType());
        meal.setDietaryRestriction(request.getDietaryRestriction());
        meal.setIngredients(request.getIngredients());
        meal.setImageUrl(request.getImageUrl());
        meal.setRequiresAdvanceBooking(request.getRequiresAdvanceBooking());
        meal.setAdvanceBookingHours(request.getAdvanceBookingHours());
        meal.setDisplayOrder(request.getDisplayOrder());

        Meal saved = mealRepository.save(meal);
        return MealMapper.toDTO(saved);
    }

    @Override
    public List<MealResponse> getByAirlineId(Long userId) {
        AirlineResponse airlineResp = airlineClient.getAirlineByOwner(userId);
        return mealRepository.findByAirlineId(airlineResp.getId())
                .stream().map(
                        MealMapper::toDTO
                ).collect(Collectors.toList());
    }

    @Override
    public void deleteMeal(Long id) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("Meal not found")
        );
        mealRepository.delete(meal);
    }

    @Override
    public MealResponse updateAvailability(Long id, Boolean availability) throws Exception {
        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new Exception("Meal not found")
        );
        meal.setAvailable(availability);
        Meal saved = mealRepository.save(meal);
        return MealMapper.toDTO(saved);
    }
}
