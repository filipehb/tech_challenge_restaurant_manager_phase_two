package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto;

import java.util.List;

public record RestaurantInputDto(
        String name,
        String address,
        TypeKitchen typeKitchen,
        RestaurantScheduleDto restaurantSchedule,
        Long owner,
        List<MenuItemInputDto> menuItems
) {
}
