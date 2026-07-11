package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import java.util.Optional;

public interface RestaurantGateway {
    Long create(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    void delete(Long id);
    Restaurant update(Long id, Restaurant restaurant);
}
