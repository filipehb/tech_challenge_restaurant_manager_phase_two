package com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.mappers;

import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeInputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.core.dto.UserTypeOutputDto;
import com.filipearruda.tech_challenge_restaurant_manager_phase_two.infra.web.json.UserTypeJson;
import jakarta.validation.Valid;

public class UserTypeMapper {
    public UserTypeInputDto mapToDto(@Valid UserTypeJson userTypeJson) {
        return new UserTypeInputDto(userTypeJson.name());
    }

    public UserTypeJson mapToJson(UserTypeOutputDto userTypeOutputDto) {
        return new UserTypeJson(userTypeOutputDto.name());
    }
}
