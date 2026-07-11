package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto;

import java.util.List;

public record RestaurantOutputDto(
        String name,
        String address,
        TypeKitchen typeKitchen,
        RestaurantScheduleDto restaurantSchedule,
        Long owner,
        List<MenuItemOutputDto> menuItems
) {
}
