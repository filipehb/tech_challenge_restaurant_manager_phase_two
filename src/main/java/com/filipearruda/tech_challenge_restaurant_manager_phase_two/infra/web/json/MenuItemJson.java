package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json;

public record MenuItemJson(
        String name,
        Double price,
        String description,
        String image,
        boolean availableOnlyOnSite
) {
}
