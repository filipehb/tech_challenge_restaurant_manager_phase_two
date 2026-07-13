package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.usecases;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.MenuItem;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.Restaurant;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.MenuItemNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.exceptions.RestaurantNotFoundException;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.MenuItemGateway;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.gateway.RestaurantGateway;

public class AssociateMenuItemUseCaseImpl implements AssociateMenuItemUseCase {

    private final RestaurantGateway restaurantGateway;
    private final MenuItemGateway menuItemGateway;

    public AssociateMenuItemUseCaseImpl(RestaurantGateway restaurantGateway, MenuItemGateway menuItemGateway) {
        this.restaurantGateway = restaurantGateway;
        this.menuItemGateway = menuItemGateway;
    }

    @Override
    public Restaurant associateMenuItemToRestaurant(Long restaurantId, Long menuItemId) {
        Restaurant restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId));

        MenuItem menuItem = menuItemGateway.findById(menuItemId)
                .orElseThrow(() -> new MenuItemNotFoundException("MenuItem not found with id: " + menuItemId));

        restaurant.addMenuItem(menuItem);

        return restaurantGateway.update(restaurantId, restaurant);
    }
}
