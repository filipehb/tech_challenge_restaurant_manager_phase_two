package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.UserJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMapper {
    private final UserTypeMapper userTypeMapper;

    public UserInputDto mapToDto(@Valid UserJson userJson) {
        return new UserInputDto(userJson.email(), userJson.password(), userJson.name());
    }

    public UserJson mapToJson(UserOutputDto userOutputDto) {
        return new UserJson(
                userOutputDto.email(),
                null,
                userOutputDto.name(),
                userOutputDto.userType() != null ? userTypeMapper.mapToJson(userOutputDto.userType()) : null
        );
    }
}
