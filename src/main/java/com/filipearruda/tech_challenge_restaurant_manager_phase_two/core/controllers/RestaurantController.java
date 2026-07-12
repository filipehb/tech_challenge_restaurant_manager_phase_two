package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.controllers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.RestaurantOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers.RestaurantMapper;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.CreateRestaurantUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.DeleteRestaurantUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.GetRestaurantUseCase;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases.UpdateRestaurantUseCase;

public class RestaurantController {
    private final RestaurantMapper restaurantMapper;
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;

    public RestaurantController(
            RestaurantMapper restaurantMapper,
            CreateRestaurantUseCase createRestaurantUseCase,
            GetRestaurantUseCase getRestaurantUseCase,
            DeleteRestaurantUseCase deleteRestaurantUseCase,
            UpdateRestaurantUseCase updateRestaurantUseCase) {
        this.restaurantMapper = restaurantMapper;
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
    }

    public Long create(RestaurantInputDto restaurantInputDto) {
        Restaurant restaurant = restaurantMapper.mapToDomain(restaurantInputDto);
        return createRestaurantUseCase.create(restaurant);
    }

    public void delete(Long restaurantId) {
        deleteRestaurantUseCase.delete(restaurantId);
    }

    public RestaurantOutputDto get(Long restaurantId) {
        Restaurant restaurant = getRestaurantUseCase.get(restaurantId);
        return restaurantMapper.mapToOutputDto(restaurant);
    }

    public RestaurantOutputDto update(Long restaurantId, RestaurantInputDto restaurantInputDto) {
        Restaurant restaurant = restaurantMapper.mapToDomain(restaurantInputDto);
        Restaurant updated = updateRestaurantUseCase.update(restaurantId, restaurant);
        return restaurantMapper.mapToOutputDto(updated);
    }
}
