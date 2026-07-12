package com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.domain.User;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserOutputDto;

public class UserMapper {
    private final UserTypeMapper userTypeMapper;

    public UserMapper(UserTypeMapper userTypeMapper) {
        this.userTypeMapper = userTypeMapper;
    }

    public User mapToDomain(UserInputDto userInputDto) {
        return new User(userInputDto.email(), userInputDto.password(), userInputDto.name());
    }

    public UserOutputDto mapToOutputDto(User user) {
        return new UserOutputDto(
                user.getEmail(),
                user.getName(),
                user.getUserType() != null ? userTypeMapper.mapToOutputDto(user.getUserType()) : null
        );
    }
}
