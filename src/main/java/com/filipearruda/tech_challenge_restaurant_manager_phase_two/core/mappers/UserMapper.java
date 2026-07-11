package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserInputDto;

public class UserMapper {
    public User mapToDomain(UserInputDto userInputDto) {
        return new User(userInputDto.email(), userInputDto.password(), userInputDto.name());
    }
}
