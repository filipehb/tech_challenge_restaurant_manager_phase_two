package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;

public interface GetRestaurantUseCase {
    Restaurant get(Long restaurantId);
}
