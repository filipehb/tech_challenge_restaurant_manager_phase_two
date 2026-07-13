package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;

public interface AssociateMenuItemUseCase {
    Restaurant associateMenuItemToRestaurant(Long restaurantId, Long menuItemId);
}
