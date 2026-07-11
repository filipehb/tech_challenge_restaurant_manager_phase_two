package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.UserJson;
import jakarta.validation.Valid;

public class UserMapper {
    public UserInputDto mapToDto(@Valid UserJson userJson) {
        return new UserInputDto(userJson.email(), userJson.password(), userJson.name());
    }
}
