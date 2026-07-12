package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserJson(
        @Email
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password,
        @NotBlank(message = "Name cannot be blank")
        String name,
        UserTypeJson userType
) {
}
