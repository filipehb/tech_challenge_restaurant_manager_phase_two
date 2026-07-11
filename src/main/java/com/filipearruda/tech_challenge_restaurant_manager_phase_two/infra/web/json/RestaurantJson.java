package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.TypeKitchen;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RestaurantJson(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Address cannot be blank")
        String address,
        @NotNull(message = "Type kitchen cannot be blank")
        TypeKitchen typeKitchen,
        @NotNull(message = "Restaurant schedule cannot be blank")
        RestaurantScheduleJson restaurantSchedule,
        @NotNull(message = "Owner cannot be blank")
        Long owner,
        List<MenuItemJson> menuItems
) {
}
