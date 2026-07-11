package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserJson(
        @Email
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password,
        @NotBlank(message = "Name cannot be blank")
        String name
) {
}
