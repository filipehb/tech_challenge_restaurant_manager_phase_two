package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto;

public record MenuItemInputDto(
        String name,
        Double price,
        String description,
        String image,
        boolean availableOnlyOnSite
) {
}
