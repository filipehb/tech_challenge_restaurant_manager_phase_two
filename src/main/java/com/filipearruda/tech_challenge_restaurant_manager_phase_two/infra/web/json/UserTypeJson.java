package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json;

import jakarta.validation.constraints.NotBlank;

public record UserTypeJson(
        @NotBlank(message = "Name cannot be blank")
        String name
) {
}