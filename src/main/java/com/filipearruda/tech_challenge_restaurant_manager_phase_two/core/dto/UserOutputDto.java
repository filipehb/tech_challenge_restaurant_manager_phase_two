package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto;

public record UserOutputDto(
        String email,
        String name,
        UserTypeOutputDto userType
) {
}
